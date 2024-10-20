package com.scheduler.server.webapp.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;

public class NotFoundException extends AppException {

    NotFoundException(HttpStatus status, String message) {
        super(status, message);
        // TODO Auto-generated constructor stub
    }

}
