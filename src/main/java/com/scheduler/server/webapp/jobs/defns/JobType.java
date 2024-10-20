package com.scheduler.server.webapp.jobs.defns;

public enum JobType {
    DELETE_USER(10),
    SEND_EMAIL(20),
    SEND_SMS(30),
    SEND_WHATSAPP_NOTIFICATION(40),
    EXPORT_JOBS_AUDIT_TO_CSV(50),
    REPORT_JOBS_TO_MAIL(60);

    int value;

    JobType(int value) {
        this.value = value;
    }

}
