package com.livedocs.server.webapp.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AppException extends Exception {

    HttpStatus status;
    String message;

}
