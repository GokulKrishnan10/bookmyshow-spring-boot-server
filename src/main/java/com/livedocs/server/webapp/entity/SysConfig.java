package com.livedocs.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = SysConfig.TABLE_NAME)
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
    @Column(name = "encrypted", columnDefinition = "boolean default false")
    private Boolean encrypted;
    @Column(name = "comment", nullable = false)
    private String comment;
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

}
