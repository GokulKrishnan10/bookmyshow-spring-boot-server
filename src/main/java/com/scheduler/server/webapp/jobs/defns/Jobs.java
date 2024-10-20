package com.scheduler.server.webapp.jobs.defns;

public enum Jobs {
    DELETE_USER(10),
    SEND_EMAIL(20),
    SEND_SMS(30),
    SEND_WHATSAPP_NOTIFICATION(40),
    EXPORT_JOBS_AUDIT_TO_CSV(50),
    REPORT_JOBS_TO_MAIL(60);

    int value;

    Jobs(int value) {
        this.value = value;
    }

}
