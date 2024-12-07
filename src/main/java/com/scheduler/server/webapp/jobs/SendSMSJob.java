package com.scheduler.server.webapp.jobs;

import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Component
public class SendSMSJob extends ScheduledJob {

    @Override
    public String executeJob() throws Exception {
        final String ACCOUNT_SID = "AC7ec418b9125c0fc5a99214f0bf220400";
        final String AUTH_TOKEN = "2a1f97fec38d1e1e3651817917c40ca3";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message.creator(new PhoneNumber(this.params.get("to").getAsString()),
                new PhoneNumber(this.params.get("from").getAsString()),
                this.params.get("body").getAsString()).create();
        return "SMS sent successfully";
    }

    @Override
    public JobType getJobType() {
        return JobType.SEND_SMS;
    }

}
