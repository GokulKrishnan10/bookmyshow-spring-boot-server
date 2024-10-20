package com.scheduler.server.webapp.entity;

import java.io.Serializable;
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
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = Job.TABLE_NAME, schema = "\"jobs\"", indexes = {
        @Index(name = "scheduled_at_index", columnList = "scheduled_at") })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Job implements Serializable {
    public static final String TABLE_NAME = "jobs";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_name", nullable = false)
    private String jobName;

    @Column(name = "params")
    private String params;

    @Column(name = "scheduled_at", nullable = false)
    private Timestamp scheduledAt;

}
