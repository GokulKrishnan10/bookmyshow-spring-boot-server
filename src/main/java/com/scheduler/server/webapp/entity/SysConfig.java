package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.scheduler.server.webapp.enums.Region;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = SysConfig.TABLE_NAME, schema = "\"app_users\"", indexes = {
        @Index(name = "idx_type_is_enabled", columnList = "type,is_enabled")
})
@Builder
@Getter
public class SysConfig {
    public static final String TABLE_NAME = "sys_configs";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "config_name", nullable = false, unique = true)
    private String configName;

    @Column(name = "config_value", nullable = false, columnDefinition = "text")
    private String configValue;

    @Column(name = "is_enabled", columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean isEnabled = true;

    @Column(name = "encrypted", columnDefinition = "boolean default false")
    private Boolean encrypted;

    @Column(name = "type")
    private String type;

    @Column(name = "comment", nullable = false, columnDefinition = "text default 'No comments'")
    private String comment;

    @Column(name = "region", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Region region;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "modified_at", nullable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
}
