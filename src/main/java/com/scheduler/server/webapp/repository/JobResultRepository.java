package com.scheduler.server.webapp.repository;

import com.scheduler.server.webapp.entity.JobResult;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobResultRepository extends JpaRepository<JobResult, Long> {

}
