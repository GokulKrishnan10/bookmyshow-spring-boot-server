package com.scheduler.server.webapp.jobs.defns;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.services.JobService;
import com.scheduler.server.webapp.services.RowLockService;

@Component
public abstract class ScheduledJob {

    @Autowired
    JobService jobService;

    private String exceptionMessage;

    private JobStatus status = JobStatus.SUCCESS;

    @Autowired
    RowLockService rowLockService;

    protected JsonObject params;

    public void initialize(JsonObject params) {
        this.params = params;
    }

    public JobStatus getJobStatus() {
        return this.status;
    }

    public String getException() {
        return this.exceptionMessage;
    }

    public void runTask() {
        try {
            this.executeJob();
        } catch (Exception exception) {
            this.exceptionMessage = exception.getMessage();
            this.status = JobStatus.FAILED;
        }
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
                    .scheduledAt(java.sql.Timestamp
                            .from(Instant.now().plus(Duration.ofDays(jobType.getFrequency().getDays()))))
                    .build();
            jobService.scheduleJob(job);
        }
    }

}
