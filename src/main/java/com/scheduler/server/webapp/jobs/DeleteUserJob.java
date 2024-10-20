package com.scheduler.server.webapp.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.scheduler.server.webapp.jobs.defns.JobType;
import com.scheduler.server.webapp.services.UserService;

@Component
public class DeleteUserJob implements ScheduledJob {

    JsonObject params;

    @Autowired
    private UserService service;

    // public DeleteUserJob(UserService service) {
    // this.service = service;
    // }

    @Override
    public String executeJob() {
        System.out.println("Inside the Delete User job " + params.get("email") + " and " + this.service);
        if (params == null) {
            return "Params not provided";
        }
        String message = this.service.deleteUserByEmail(params.get("email").getAsString());
        System.out.println(message);
        return "User deletion successful";
    }

    @Override
    public void initialize(JsonObject params) {
        this.params = params;
    }

    @Override
    public JobType getJobType() {
        return JobType.DELETE_USER;
    }
}
