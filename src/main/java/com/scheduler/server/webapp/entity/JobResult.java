package com.scheduler.server.webapp.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import com.scheduler.server.webapp.enums.JobStatus;
import com.scheduler.server.webapp.enums.JobType;
import lombok.*;

@Entity
@Table(name = JobResult.TABLE_NAME, schema = "\"jobs\"")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JobResult {
    public static final String TABLE_NAME = "job_results";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "job_name", nullable = false)
    private JobType jobName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "scheduled_at", nullable = false)
    private Timestamp scheduledAt;

    @Column(name = "error", nullable = false, columnDefinition = "text")
    private String error;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
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
