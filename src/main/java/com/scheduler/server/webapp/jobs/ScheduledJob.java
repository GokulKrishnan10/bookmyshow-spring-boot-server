package com.scheduler.server.webapp.jobs;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.enums.JobType;

public interface ScheduledJob {
    void initialize(JsonObject params);

    String executeJob();

    JobType getJobType();
}
