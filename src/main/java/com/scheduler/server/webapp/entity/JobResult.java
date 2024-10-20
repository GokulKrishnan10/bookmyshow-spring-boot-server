package com.scheduler.server.webapp.entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.jobs.defns.JobType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import lombok.Builder;

@Entity
@Table(name = JobResult.TABLE_NAME, schema = "\"jobs\"")
@Builder
public class JobResult {
    public static final String TABLE_NAME = "job_results";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private JobType jobName;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "scheduled_at", nullable = false)
    private Timestamp scheduledAt;

    @Column(name = "error", nullable = false)
    private String error;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;

    @Column(name = "executed_at", nullable = false)
    private Timestamp executedAt;

    @Column(name = "picked_by")
    private String pickedBy;

    @Column(name = "execution_time")
    private String executionTime;

    @Column(name = "number_of_failures")
    private int numberOfFailures;
}
