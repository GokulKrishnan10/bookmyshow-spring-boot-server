package com.scheduler.server.webapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = AppJob.TABLE_NAME, schema = "\"jobs\"")
@Builder
@Getter
public class AppJob {
    public final static String TABLE_NAME = "app_jobs";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "method_mapping", nullable = false)
    private String methodMapping;

    @Column(name = "is_recurring", nullable = false)
    private Boolean isRecurring;

    @Column(name = "is_notification", nullable = false)
    private Boolean isNotification;
}
