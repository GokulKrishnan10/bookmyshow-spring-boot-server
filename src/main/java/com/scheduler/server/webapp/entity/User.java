package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.LinkedList;

import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.scheduler.server.webapp.enums.UserRole;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = User.TABLE_NAME, schema = "\"app_users\"", indexes = {
        @Index(name = "user_email_index", columnList = "email", unique = true),
        @Index(name = "user_phone_number_index", columnList = "phone_number", unique = true) })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User implements UserDetails {
    public static final String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "dob", nullable = false)
    private Timestamp dob;

    @Column(name = "country")
    private String country;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CurrentTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @CurrentTimestamp
    private Timestamp updatedAt;

    @Column(name = "is_expired", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isExpired = false;

    @Column(name = "is_locked", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isLocked = false;

    @Column(name = "is_enabled", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean isEnabled = false;

    @Column(name = "role", nullable = false, columnDefinition = "text default 'USER'")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<UserRole> list = new LinkedList<>();
        list.add(role);
        return list;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
