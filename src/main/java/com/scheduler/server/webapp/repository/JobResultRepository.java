package com.scheduler.server.webapp.repository;

import java.util.List;

import com.scheduler.server.webapp.entity.JobResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JobResultRepository extends JpaRepository<JobResult, Long> {
    // @Query("select results from JobResult results limit 1000")
    // List<JobResult> getRecordsByLimit();
}
