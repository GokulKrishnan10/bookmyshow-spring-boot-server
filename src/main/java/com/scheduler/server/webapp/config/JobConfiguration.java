package com.scheduler.server.webapp.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfiguration {
    @Bean
    public Map<JobType, ScheduledJob> jobMap(ApplicationContext context) {
        Map<JobType, ScheduledJob> map = new HashMap<>();
        Map<String, ScheduledJob> jobBeans = context.getBeansOfType(ScheduledJob.class);
        for (ScheduledJob job : jobBeans.values()) {
            JobType type = job.getJobType();
            map.put(type, job);
        }
        return map;
    }
}
