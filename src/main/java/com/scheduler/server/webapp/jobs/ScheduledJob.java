package com.scheduler.server.webapp.jobs;

import com.google.gson.JsonObject;

public interface ScheduledJob {
    void initialize(JsonObject params);

    String executeJob();
}
