package com.scheduler.server.webapp.controller;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scheduler.server.webapp.response.JsonResponse;

@RestController
@RequestMapping("/api/ms")
public class MicroserviceController {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @RequestMapping("/payments")
    public ResponseEntity<JsonResponse> makePaymentsCall(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            Object data = response.body();
            return ResponseEntity
                    .ok(JsonResponse.builder().data(data).message("successful").status(HttpStatus.OK).build());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(JsonResponse.builder().message("Error occurred").status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
        }
    }

    @RequestMapping("/orders")
    public ResponseEntity<JsonResponse> makeOrdersMsCall() {
        return ResponseEntity.ok(new JsonResponse());
    }

    @RequestMapping("/fileupload")
    public ResponseEntity<JsonResponse> makeFileUploadMsCall() {
        return ResponseEntity.ok(new JsonResponse());
    }

}
