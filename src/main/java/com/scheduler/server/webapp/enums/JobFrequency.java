package com.scheduler.server.webapp.enums;

public enum JobFrequency {
    DAILY(0),
    WEEKLY(100),
    MONTHLY(200),
    QUARTERLY(300),
    YEARLY(400),
    NONE(500);

    int value;

    JobFrequency(int value) {
        this.value = value;
    }

    public int getJobFrequency() {
        return this.value;
    }

}
