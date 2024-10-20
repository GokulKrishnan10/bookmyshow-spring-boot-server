package com.scheduler.server.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scheduler.server.webapp.entity.AppJob;

public interface AppJobRepository extends JpaRepository<AppJob, Long> {
    AppJob findByJobId(Long jobId);

    List<AppJob> findAll();
}
