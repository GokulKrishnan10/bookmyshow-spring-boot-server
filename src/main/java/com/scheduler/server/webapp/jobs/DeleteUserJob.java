package com.scheduler.server.webapp.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.UserService;

@Component
public class DeleteUserJob extends ScheduledJob {

    @Autowired
    private UserService service;

    // public DeleteUserJob(UserService service) {
    // this.service = service;
    // }

    @Override
    public String executeJob() {
        System.out.println("User deletion Job");
        if (params == null) {
            System.out.println("User deletion Job---> Params is null");
            return "Params not provided";
        }

        String message = this.service.deleteUserByEmail(params.get("email").getAsString());
        System.out.println(message);
        return "User deletion successful";
    }

    @Override
    public JobType getJobType() {
        return JobType.DELETE_USER;
    }
}
