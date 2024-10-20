package com.scheduler.server.webapp.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppException extends RuntimeException {

    HttpStatus status;
    String message;

}
