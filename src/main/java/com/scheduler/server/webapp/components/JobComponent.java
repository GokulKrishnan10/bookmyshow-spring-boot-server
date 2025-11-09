package com.scheduler.server.webapp.components;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.JobService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JobComponent {

    private static final Logger logger = LoggerFactory.getLogger(JobComponent.class);
    private static final Gson gson = new Gson();

    @Autowired
    private JobService jobService;

    private final Map<JobType, ScheduledJob> jobMap;

    public JobComponent(Map<JobType, ScheduledJob> jobMap) {
        this.jobMap = jobMap;
    }

    @Scheduled(fixedRate = 100)
    @Async
    public void readFromJobTable() {
        List<Job> jobs = jobService.getJobByTimeRange();

        jobs.forEach(schJob -> {
            try {
                executeJob(schJob.getJobName(), getJsonFromString(schJob, schJob.getParams()), schJob);
            } catch (Exception e) {
                logger.error("Error executing job: {}", schJob.getJobName(), e);
            }
        });
    }

    private ScheduledJob getScheduledJob(JobType jobType) {
        return this.jobMap.get(jobType);
    }

    private void executeJob(JobType jobName, JsonObject params, Job schJob) throws Exception {
        ScheduledJob job = getScheduledJob(jobName);
        boolean lock = job.lockTheJob(schJob);

        if (lock) {
            job.initialize(params);
            job.validate();
            jobService.saveStatus(schJob.getId(), JobStatus.RUNNING);
            logger.info("Job is being executed {} and job id is {}", job.getJobType(), schJob.getId());
            job.runTask(schJob);
            jobService.saveStatus(schJob.getId(), JobStatus.SUCCESS);
            job.recurringSchedule();
        }
    }

    private JsonObject getJsonFromString(Job schJob, String params) throws Exception {
        try {
            return gson.fromJson(params, JsonObject.class);
        } catch (Exception exception) {
            jobService.insertFailedStatus(schJob, exception.getMessage());
            throw new Exception("Invalid parameters for job " + schJob.getJobName());
        }

    }

    public void invokeMethodFromClass(String className, JsonObject params) {
        try {
            // job.initialize(params);
            // job.executeJob();
        } catch (Exception ex) {
            throw AppException.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Job Definition not found " + ex.getLocalizedMessage())
                    .build();
        }
    }
}
