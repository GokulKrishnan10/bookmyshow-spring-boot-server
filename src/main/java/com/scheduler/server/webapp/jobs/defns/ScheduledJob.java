package com.scheduler.server.webapp.jobs.defns;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.services.JobService;
import com.scheduler.server.webapp.services.RowLockService;

@Component
public abstract class ScheduledJob {

    @Autowired
    JobService jobService;

    @Autowired
    RowLockService rowLockService;

    protected JsonObject params;

    public void initialize(JsonObject params) {
        this.params = params;
    }

    abstract public String executeJob() throws Exception;

    abstract public JobType getJobType();

    public boolean lockTheJob(Job job) {
        if (this.checkIfLocked(job.getId())) {
            return false;
        }
        rowLockService.lockJob(job);
        return true;
    }

    public void removeLock(Long id) {
        rowLockService.removeLock(id);
    }

    public boolean checkIfLocked(Long id) {
        return rowLockService.checkIfLocked(id);
    }

    public void recurringSchedule() {
        JobType jobType = this.getJobType();
        if (jobType.isRecurring()) {
            String jsonString = this.params.toString();
            Job job = Job.builder().jobName(jobType).params(jsonString)
                    .scheduledAt(java.sql.Timestamp.from(Instant.now().plus(Duration.ofDays(30)))).build();
            jobService.scheduleJob(job);
        }
    }
}
