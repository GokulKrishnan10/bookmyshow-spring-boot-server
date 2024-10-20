package com.scheduler.server.webapp.components;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.jobs.DeleteUserJob;
import com.scheduler.server.webapp.services.JobService;

@Component
public class JobComponent {

    @Autowired
    JobService jobService;

    @Autowired
    DeleteUserJob job;

    @Scheduled(fixedRate = 500)
    public void readFromJobTable() {
        List<Job> jobs = jobService.getJobByTimeRange();
        if (!jobs.isEmpty()) {
            System.out.println("Job list " + jobs.toString() + " executed at " + Timestamp.from(Instant.now()));
        }
        jobs.forEach(schJob -> {
            invokeMethodFromClass("com.scheduler.server.webapp.jobs." + schJob.getJobName(),
                    getJsonFromString(schJob.getParams()));
            jobService.deleteById(schJob.getId());
            jobService.insertSuccess(schJob, JobStatus.SUCCESS);
        });

    }

    public JsonObject getJsonFromString(String params) {
        JsonObject json = new Gson().fromJson(params, JsonObject.class);
        return json;
    }

    public void invokeMethodFromClass(String className, JsonObject params) {
        try {
            job.initialize(params);
            job.executeJob();
        } catch (Exception ex) {
            throw AppException.builder().status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("Job Definition not found " + ex.getLocalizedMessage())
                    .build();
        }
    }
}
