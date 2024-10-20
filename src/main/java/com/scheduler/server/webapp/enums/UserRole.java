package com.scheduler.server.webapp.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN(10),
    USER(20),
    SALES(30),
    MARKETING(40);

    int value;

    UserRole(int value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.name();
    }
}
