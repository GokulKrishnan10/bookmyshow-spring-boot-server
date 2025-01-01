package com.scheduler.server.webapp.enums;

public enum JobType {
    DELETE_USER(10),
    SEND_EMAIL(20),
    SEND_SMS(30),
    SEND_WHATSAPP_NOTIFICATION(40),
    EXPORT_JOBS_AUDIT_TO_CSV(50, true, JobFrequency.MONTHLY),
    REPORT_JOBS_TO_MAIL(60),
    SEND_MONTHLY_REPORT(70, true, JobFrequency.MONTHLY),
    RUN_DAILY_TESTS(80, true, JobFrequency.DAILY), // this is supposed to be executed daily at 12am IST
    UPDATE_JWT_SIGNING_KEYS(90, true, JobFrequency.WEEKLY);

    int value;
    boolean isRecurring = false;
    JobFrequency frequency = JobFrequency.NONE;

    JobType(int value) {
        this.value = value;
    }

    JobType(int value, boolean isRecurring) {
        this(value);
        this.isRecurring = isRecurring;
    }

    JobType(int value, boolean isRecurring, JobFrequency frequency) {
        this(value, isRecurring);
        this.frequency = frequency;
    }

    public boolean isRecurring() {
        return this.isRecurring;
    }

    public JobFrequency getFrequency() {
        return this.frequency;
    }

}
