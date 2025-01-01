package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.scheduler.server.webapp.enums.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = SysConfig.TABLE_NAME, schema = "\"app_users\"")
@Builder
@Getter
public class SysConfig {
    public static final String TABLE_NAME = "sys_configs";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_name", nullable = false)
    private String configName;

    @Column(name = "config_value", nullable = false)
    private String configValue;

    @Column(name = "is_enabled", columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean isEnabled = false;

    @Column(name = "encrypted", columnDefinition = "boolean default false")
    private Boolean encrypted;

    @Column(name = "comment", nullable = false, columnDefinition = "text default 'No comments'")
    private String comment;

    @Column(name = "region", nullable = false)
    private Region region;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

}
