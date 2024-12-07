package com.scheduler.server.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduler.server.webapp.entity.JobMapping;

public interface AppJobRepository extends JpaRepository<JobMapping, Long> {
    JobMapping findByJobId(Long jobId);

    List<JobMapping> findAll();
}
