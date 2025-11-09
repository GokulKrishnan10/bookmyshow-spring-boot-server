package com.scheduler.server.webapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "private_keys", schema = "\"jobs\"")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PrivateKeys implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "key_name", nullable = false, unique = true)
    private String keyName;

    // keep field name `key` as requested; store value in 'key_value' column to
    // avoid reserved-word issues
    @Column(name = "key_value", columnDefinition = "text", nullable = false)
    private String key;

    @Column(name = "last_rotated_at")
    private Timestamp lastRotatedAt;

    @Column(name = "created_at", nullable = false)
    @CurrentTimestamp
    private Timestamp createdAt;
}
