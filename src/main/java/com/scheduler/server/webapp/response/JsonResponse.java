package com.scheduler.server.webapp.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JsonResponse {
    private HttpStatus status;
    private String message;
    private Object data;

}
