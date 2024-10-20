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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(JsonResponse.builder()
                                                .message("Some input parameters missing/have problems" + e.getMessage())
                                                .status(HttpStatus.BAD_REQUEST).build());
        }

        @ExceptionHandler(AppException.class)
        public ResponseEntity<Object> handleException(AppException ex) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(JsonResponse.builder()
                                                .message("Cannot do this operation, as you are barred from this"
                                                                + ex.getMessage())
                                                .status(HttpStatus.FORBIDDEN).build());
        }

        @ExceptionHandler(UsernameNotFoundException.class)
        public ResponseEntity<Object> handleExceptionForUser(UsernameNotFoundException ex) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(JsonResponse.builder()
                                                .message("Specified User not Found"
                                                                + ex.getMessage())
                                                .status(HttpStatus.NOT_FOUND).build());
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<Object> handleGeneralException(Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(JsonResponse.builder()
                                                .message(e.getMessage())
                                                .status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }

}
