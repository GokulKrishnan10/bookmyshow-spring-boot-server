package com.livedocs.server.webapp.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.Builder;

@Entity
@Table(name = UserMFA.TABLE_NAME)
@Builder
public class UserMFA {
    public static final String TABLE_NAME = "users_mfa";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "secret_key")
    private String key;

    @Column(name = "mfa_enabled")
    private Boolean mfaEnabled;

    @Column(name = "qr_image")
    private String qrImage;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

}
