package com.scheduler.server.webapp.enums;

public enum JobFrequency {
    DAILY(0, 1),
    WEEKLY(100, 7),
    MONTHLY(200, 30),
    QUARTERLY(300, 90),
    YEARLY(400, 365),
    NONE(500, 0);

    int value, days;

    JobFrequency(int value, int days) {
        this.value = value;
        this.days = days;
    }

    public int getJobFrequency() {
        return this.value;
    }

    public int getDays() {
        return this.days;
    }

}
