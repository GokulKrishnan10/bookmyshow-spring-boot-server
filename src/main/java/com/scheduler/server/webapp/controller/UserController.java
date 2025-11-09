package com.scheduler.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.User;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.response.JsonResponse;
import com.scheduler.server.webapp.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.ok(JsonResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("User list returned")
                .data(service.getAll()).build());
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponse.builder()
                .data(service.saveCustomer(entity))
                .message("User created successfully")
                .status(HttpStatus.OK)
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User entity) {
        // TODO: process POST request
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.builder().data("success").build());
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteUserByEmail(@RequestBody String body) throws AppException {
        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String email = json.get("email").getAsString();
        return ResponseEntity.ok(service.deleteUserByEmail(email));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody User entity) {
        // TODO: process PUT request
        return ResponseEntity.ok("Success");
    }
}
