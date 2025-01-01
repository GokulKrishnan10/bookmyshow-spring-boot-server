package com.scheduler.server.webapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "row_locks", schema = "\"transactions\"", indexes = {
        @Index(name = "job_id_index", columnList = "job_id"),
        @Index(name = "_id_index", columnList = "id") })
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RowLock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id", unique = true)
    private Long jobId;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CurrentTimestamp
    private Timestamp createdAt;
}
