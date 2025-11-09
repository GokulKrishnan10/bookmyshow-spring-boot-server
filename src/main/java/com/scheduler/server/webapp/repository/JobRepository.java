package com.scheduler.server.webapp.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobStatus;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.scheduledAt BETWEEN :startTimestamp AND :endTimestamp")
    List<Job> findAllByScheduledAtInRange(Timestamp startTimestamp, Timestamp endTimestamp);

    @Query("SELECT j FROM Job j WHERE j.scheduledAt = :time")
    List<Job> getAllOutDatedJobs(Timestamp time);

    @Modifying
    @Transactional
    @Query("DELETE FROM Job j WHERE j.status = 'SUCCESS'")
    void deleteAllSuccessJobs();

    @Query(value = "SELECT * FROM jobs.jobs j WHERE j.id = :jobId FOR UPDATE", nativeQuery = true)
    Optional<Job> lockRecord(Long jobId);

    void deleteById(Long jobId);

    @Modifying
    @Transactional
    @Query("UPDATE Job j SET j.status = :status WHERE j.id = :jobId")
    void updateJobStatus(JobStatus status, Long jobId);
}
