package com.scheduler.server.webapp.controller;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scheduler.server.webapp.response.JsonResponse;

@RequestMapping("/api/ms")
public class MicroserviceController {

    @RequestMapping("/payments")
    public ResponseEntity<JsonResponse> makePaymentsCall(HttpRequest request) {
        try {
            HttpResponse<Object> response = HttpClient.newHttpClient().send(request, null);
            Object data = response.body();
            return ResponseEntity
                    .ok(JsonResponse.builder().data(data).message("successful").status(HttpStatus.OK).build());
        } catch (IOException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok(new JsonResponse());
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
