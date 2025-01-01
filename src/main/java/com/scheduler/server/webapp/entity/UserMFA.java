package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

import lombok.Builder;

@Entity
@Table(name = UserMFA.TABLE_NAME, schema = "\"app_users\"")
@Builder
public class UserMFA {
    public static final String TABLE_NAME = "users_mfa";

    @Id
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "secret_key", nullable = false)
    private String key;

    @Column(name = "mfa_enabled", nullable = false)
    private Boolean mfaEnabled;

    @Column(name = "qr_image", nullable = false)
    private String qrImage;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

}
