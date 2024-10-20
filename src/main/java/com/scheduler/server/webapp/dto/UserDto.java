package com.scheduler.server.webapp.dto;

public record UserDto(
                String name,
                String phoneNumber,
                String dob,
                String country,
                String email) {
}
