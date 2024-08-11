package com.scheduler.server.webapp.dto;

public record UserDto(Long id, String name, int age, String phoneNumber,
                String dob, String country, String email) {
}
