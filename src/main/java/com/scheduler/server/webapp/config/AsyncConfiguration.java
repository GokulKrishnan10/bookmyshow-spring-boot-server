package com.scheduler.server.webapp.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors();
        executor.setQueueCapacity(100);
        // executor.setMaxPoolSize(processors * 2 + 1);
        executor.setCorePoolSize(processors);
        executor.setThreadNamePrefix("job-scheduler-thread-");
        executor.initialize();
        return executor;
    }
}
