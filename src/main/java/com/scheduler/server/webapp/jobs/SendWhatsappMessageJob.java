package com.scheduler.server.webapp.jobs;

import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;

@Component
public class SendWhatsappMessageJob extends ScheduledJob {

    @Override
    public String executeJob() throws Exception {
        return "";
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_WHATSAPP_NOTIFICATION;
    }

}
