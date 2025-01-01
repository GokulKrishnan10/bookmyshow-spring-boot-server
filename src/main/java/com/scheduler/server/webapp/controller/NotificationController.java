package com.scheduler.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class NotificationController {
    private final SseEmitter emitter = new SseEmitter();

    @GetMapping("/subscribe")
    public SseEmitter getMethodName(@RequestParam String param) {
        return emitter;
    }

    public void sendNotification(String message) {
        try {
            emitter.send(SseEmitter.event().data(message));
        } catch (Exception exc) {

        }
    }

}
