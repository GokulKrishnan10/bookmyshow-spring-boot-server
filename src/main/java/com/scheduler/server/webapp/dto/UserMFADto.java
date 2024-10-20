package com.scheduler.server.webapp.dto;

import java.sql.Timestamp;

public record UserMFADto(Long id, Long userId, String qrImage, Timestamp createdAt, Timestamp updatedAt,
        Boolean mfaEnabled) {

}
