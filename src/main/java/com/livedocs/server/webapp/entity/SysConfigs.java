package com.livedocs.server.webapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sys_configs")
public class SysConfigs {

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
}
