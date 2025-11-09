package com.scheduler.server.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.scheduler.server.webapp.entity.User;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.response.JsonResponse;
import com.scheduler.server.webapp.services.JwtService;
import com.scheduler.server.webapp.services.UserService;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
public class JwtController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody User user) throws AppException {
        JsonResponse response = JsonResponse.builder()
                .message("success")
                .status(HttpStatus.ACCEPTED)
                .data(service.getJwtToken(user))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public String getMethodName(@RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> System.out.println(key + " and value is " + value));
        List<String> tokens = headers.get("Authorization");
        System.out.println("Authorization Headers " + tokens);
        return "Done";
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Object> deleteUser(@RequestParam Long id,
            @RequestHeader MultiValueMap<String, String> headers) throws AppException {
        List<String> tokens = headers.get("Authorization");
        if (tokens != null && !tokens.isEmpty()) { // && jwtService.validateToken(tokens.get(0))) {
            String message = service.deleteCustomer(id);
            return ResponseEntity.ok(Map.of("message", message, "status", HttpStatus.ACCEPTED));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Unauthorized user")
                .data("Null")
                .build());
    }
}
