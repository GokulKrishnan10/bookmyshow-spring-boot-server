package com.scheduler.server.webapp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scheduler.server.webapp.response.JsonResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Object> handleInputValidation(DataIntegrityViolationException e) {
                return buildResponseEntity("Some input parameters missing/have problems" + e.getMessage(),
                                HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AppException.class)
        public ResponseEntity<Object> handleException(AppException ex) {
                return buildResponseEntity("Cannot do this operation, as you are barred from this" + ex.getMessage(),
                                HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Object> handleExceptionForUser(UsernameNotFoundException ex) {
                return buildResponseEntity("Specified User not Found" + ex.getMessage(), HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleGeneralException(Exception e) {
                return buildResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status) {
                return ResponseEntity.status(status)
                                .body(JsonResponse.builder()
                                                .message(message)
                                                .status(status).build());
        }
}
