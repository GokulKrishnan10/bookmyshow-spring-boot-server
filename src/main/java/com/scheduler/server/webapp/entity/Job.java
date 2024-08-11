package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.scheduler.server.webapp.enums.JobStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = Job.TABLE_NAME)
@Builder
@Getter
public class Job {
    public static final String TABLE_NAME = "jobs";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "execution_time", nullable = false)
    private String executionTime;

    @Column(name = "job_status")
    @Enumerated(EnumType.ORDINAL)
    private JobStatus jobStatus;

    @Column(name = "number_of_failures", columnDefinition = "int default 0")
    private int numberOfFailures;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "executed_at", nullable = false)
    private Timestamp executedAt;

    @Column(name = "picked_by", nullable = false)
    private String pickedBy;
}
