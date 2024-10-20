package com.scheduler.server.webapp.enums;

public enum JobStatus {
    SUCCESS(0, "success"),
    PENDING(10, "pending"),
    RUNNING(20, "running"),
    FAILED(30, "failed"),
    RESCHEDULED(40, "rescheduled");

    int value;
    String name;

    JobStatus(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
