package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.scheduler.server.webapp.entity.JobMapping;
import com.scheduler.server.webapp.repository.AppJobRepository;
import com.scheduler.server.webapp.exception.AppException;

public class JobMapperService {
    @Autowired
    AppJobRepository appJobRepository;

    public void insertMapper(JobMapping appJob) {
        if (!isJobPresent("com.scheduler.server.webapp.jobs" + appJob.getMethodMapping())) {
            throw AppException.builder().status(HttpStatus.BAD_REQUEST).message("Job is not present in the code")
                    .build();
        }

        JobMapping jobMapper = JobMapping.builder()
                .name(appJob.getName())
                .isRecurring(appJob.getIsRecurring())
                .isNotification(appJob.getIsNotification())
                .methodMapping(appJob.getMethodMapping()).build();
        appJobRepository.save(jobMapper);
    }

    public boolean isJobPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
