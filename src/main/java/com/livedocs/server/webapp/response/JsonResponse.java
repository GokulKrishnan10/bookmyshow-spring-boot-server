package com.livedocs.server.webapp.response;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JsonResponse {
    private HttpStatus status;
    private String message;
    private Object data;

    public static JsonResponse createResponse() {
        return new JsonResponse();
    }

    public JsonResponse setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public JsonResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public JsonResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Object getData() {
        return this.data;
    }
}
