package com.livedocs.server.webapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = Job.TABLE_NAME)
public class Job {
    public static final String TABLE_NAME = "jobs";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "execution_time", nullable = false)
    private String executionTime;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "number_of_failures", columnDefinition = "int default 0")
    private int numberOfFailures;
}
