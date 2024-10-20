package com.scheduler.server.webapp.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.scheduler.server.webapp.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select j from Job j where j.scheduledAt between ?1 and ?2")
    List<Job> findAllByScheduledAtInRange(Timestamp startTimestamp,
            Timestamp endTimestamp);
}
