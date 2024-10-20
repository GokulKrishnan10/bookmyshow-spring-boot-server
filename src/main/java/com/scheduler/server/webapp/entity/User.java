package com.scheduler.server.webapp.entity;

import java.sql.Timestamp;
import java.util.*;

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
@Table(name = User.TABLE_NAME, schema = "\"user\"", indexes = {
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

    @Column(name = "is_expired", nullable = false)
    private Boolean isExpired;

    @Column(name = "is_locked", nullable = false)
    private Boolean isLocked;

    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        Collection<UserRole> list = new LinkedList<>();
        list.add(role);
        return list;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return this.isExpired;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return this.isEnabled;
    }

}
