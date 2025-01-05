package com.scheduler.server.webapp.components;

import java.util.List;

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

import java.util.Map;

@Component
public class JobComponent {

    @Autowired
    JobService jobService;

    private final Map<JobType, ScheduledJob> jobMap;

    @Autowired
    public JobComponent(Map<JobType, ScheduledJob> jobMap) {
        this.jobMap = jobMap;
    }

    @Scheduled(fixedRate = 100)
    @Async
    public void readFromJobTable() throws Exception {
        List<Job> jobs = jobService.getJobByTimeRange();

        jobs.forEach(schJob -> {
            try {
                executeJob(schJob.getJobName(),
                        getJsonFromString(schJob.getParams()), schJob);

            } catch (Exception e) {
                e.printStackTrace();
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
            jobService.saveStatus(schJob.getId(), JobStatus.RUNNING);
            System.out.println(
                    "Job is being executed " + job.getJobType().toString() + " and job id is " + schJob.getId());
            job.runTask(schJob);
            jobService.saveStatus(schJob.getId(), JobStatus.SUCCESS);
            job.recurringSchedule();
        }

    }

    private JsonObject getJsonFromString(String params) {
        JsonObject json = new Gson().fromJson(params, JsonObject.class);
        return json;
    }

    public void invokeMethodFromClass(String className, JsonObject params) {
        try {
            // job.initialize(params);
            // job.executeJob();
        } catch (Exception ex) {
            throw AppException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Job Definition not found " + ex.getLocalizedMessage())
                    .build();
        }
    }
}
