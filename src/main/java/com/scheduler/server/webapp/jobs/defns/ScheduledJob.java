package com.scheduler.server.webapp.jobs.defns;

import java.time.Duration;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.services.JobService;
import com.scheduler.server.webapp.services.LockService;

@Component
public abstract class ScheduledJob {

    @Autowired
    private JobService jobService;

    @Autowired
    private LockService lockService;

    private JsonObject params;

    public synchronized void initialize(JsonObject params) {
        this.validateParams();
        this.params = params;
    }

    public void validateParams() {
        // Default implementation does nothing.
        // Subclasses can override to provide specific validation logic.

    }

    public synchronized JsonObject getParams() {
        return this.params;
    }

    public void runTask(Job schJob) {
        try {
            this.executeJob();
            jobService.insertSuccess(schJob);
        } catch (Exception exception) {
            jobService.insertFailedStatus(schJob, exception.getMessage());
        } finally {
            this.removeLock(schJob.getId());
            jobService.deleteJob(schJob.getId());
        }
    }

    public void validate() {
        if (this.params == null) {
            throw new IllegalStateException("Job parameters not initialized.");
        }
    }

    public abstract String executeJob() throws Exception;

    public abstract JobType getJobType();

    public boolean lockTheJob(Job job) {
        return lockService.acquireLock("job-" + job.getId(), "locked", 15);
    }

    public void removeLock(Long id) {
        lockService.releaseLock("job-" + id);
    }

    public void recurringSchedule() {
        JobType jobType = this.getJobType();
        if (jobType.isRecurring()) {
            String jsonString = this.getParams().toString();
            Job job = Job.builder()
                    .jobName(jobType)
                    .params(jsonString)
                    .scheduledAt(java.sql.Timestamp
                            .from(Instant.now().plus(Duration.ofDays(jobType.getFrequency().getDays()))))
                    .build();
            jobService.scheduleJob(job);
        }
    }
}
