package com.scheduler.server.webapp.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class JobConfiguration {
    @Bean
    public Map<JobType, ScheduledJob> jobMap(ApplicationContext context) {
        Map<JobType, ScheduledJob> map = new EnumMap<>(JobType.class);
        context.getBeansOfType(ScheduledJob.class).values().forEach(job -> map.put(job.getJobType(), job));
        return map;
    }
}
