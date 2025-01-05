package com.scheduler.server.webapp.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.enums.JobStatus;

public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("select j from Job j where j.scheduledAt between ?1 and ?2")
    List<Job> findAllByScheduledAtInRange(Timestamp startTimestamp,
            Timestamp endTimestamp);

    @Query("select j from Job j where j.scheduledAt = ?1")
    List<Job> getAllOutDatedJobs(Timestamp time);

    @Modifying
    @Query("delete from Job j where j.status = 'SUCCESS'")
    void deleteAllSuccessJobs();

    @Query(value = "select * from jobs.jobs j where j.id = ?1 for update", nativeQuery = true)
    Optional<Job> lockRecord(Long jobId);

    void deleteById(Long jobId);

    @Modifying
    @Query("update Job j set j.status = ?1 where j.id = ?2")
    void updateJobStatus(JobStatus status, Long jobId);

}
