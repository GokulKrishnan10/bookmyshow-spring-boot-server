package com.scheduler.server.webapp.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.UserService;

@Component
public class DeleteUserJob extends ScheduledJob {

    @Autowired
    private UserService service;

    @Override
    public String executeJob() {
        System.out.println("User deletion Job");
        if (getParams() == null) {
            System.out.println("User deletion Job---> Params is null");
            return "Params not provided";
        }

        String email = getParams().get("email").getAsString();
        String message = service.deleteUserByEmail(email);
        System.out.println(message);
        return "User deletion successful";
    }

    @Override
    public JobType getJobType() {
        return JobType.DELETE_USER;
    }
}
