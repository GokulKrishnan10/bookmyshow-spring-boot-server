package com.scheduler.server.webapp.entity;

import com.scheduler.server.webapp.enums.Region;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = UiAsset.TABLE_NAME, schema = "\"user\"")
@Builder
@Getter
public class UiAsset {
    public static final String TABLE_NAME = "ui_assets";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_version")
    private String tagVersion;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "cdned")
    private Boolean cdned;

    @Column(name = "region")
    @Enumerated(EnumType.ORDINAL)
    private Region region;
}
