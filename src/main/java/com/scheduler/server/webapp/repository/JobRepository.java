package com.scheduler.server.webapp.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;

import com.scheduler.server.webapp.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select j from Job j where j.scheduledAt between ?1 and ?2")
    List<Job> findAllByScheduledAtInRange(Timestamp startTimestamp,
            Timestamp endTimestamp);

    @Query("select j from Job j where j.scheduledAt = ?1")
    List<Job> getAllOutDatedJobs(Timestamp time);
}
