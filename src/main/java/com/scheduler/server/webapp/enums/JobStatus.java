package com.scheduler.server.webapp.enums;

public enum JobStatus {
    PENDING(10),
    RUNNING(20),
    FAILED(30),
    RESCHEDULED(40);

    int value;

    JobStatus(int value) {
        this.value = value;
    }
}
