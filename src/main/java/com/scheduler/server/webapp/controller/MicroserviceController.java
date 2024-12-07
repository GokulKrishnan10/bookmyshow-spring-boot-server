package com.scheduler.server.webapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scheduler.server.webapp.response.JsonResponse;

@RequestMapping("/microservice")
public class MicroserviceController {

    @RequestMapping("/payments")
    public ResponseEntity<JsonResponse> makeMsCall() {
        return ResponseEntity.ok(new JsonResponse());
    }

    @RequestMapping("/orders")
    public ResponseEntity<JsonResponse> makePaymentMsCall() {
        return ResponseEntity.ok(new JsonResponse());
    }

    @RequestMapping("/fileupload")
    public ResponseEntity<JsonResponse> makeFileUploadMsCall() {
        return ResponseEntity.ok(new JsonResponse());
    }

}
