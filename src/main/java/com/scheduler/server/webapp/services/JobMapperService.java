package com.scheduler.server.webapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.JobMapping;
import com.scheduler.server.webapp.repository.JobMappingRepository;
import com.scheduler.server.webapp.exception.AppException;

@Service
public class JobMapperService {
    private final JobMappingRepository appJobRepository;

    public JobMapperService(JobMappingRepository appJobRepository) {
        this.appJobRepository = appJobRepository;
    }

    public void insertMapper(JobMapping appJob) {
        if (!isJobPresent("com.scheduler.server.webapp.jobs." + appJob.getMethodMapping())) {
            throw AppException.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Job is not present in the code")
                    .build();
        }

        JobMapping jobMapper = JobMapping.builder()
                .name(appJob.getName())
                .isRecurring(appJob.getIsRecurring())
                .isNotification(appJob.getIsNotification())
                .methodMapping(appJob.getMethodMapping())
                .build();
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
