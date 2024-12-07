package com.scheduler.server.webapp.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JsonResponse {
    private HttpStatus status;
    private String message;
    private Object data;

}
