package com.livedocs.server.webapp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.livedocs.server.webapp.response.JsonResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleInputValidation(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(JsonResponse.createResponse()
                        .setMessage("Some input parameters missing/have problems" + e.getMessage())
                        .setStatus(HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleException() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(JsonResponse.createResponse()
                        .setMessage("Cannot do this operation, as you are barred from this")
                        .setStatus(HttpStatus.FORBIDDEN));
    }

}
