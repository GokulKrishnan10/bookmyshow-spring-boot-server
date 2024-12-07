package com.scheduler.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.scheduler.server.webapp.entity.User;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.response.JsonResponse;
import com.scheduler.server.webapp.services.JwtService;
import com.scheduler.server.webapp.services.UserService;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private JwtService jwtService;

    // Using path variable like api.example.com/gokul/name
    // @RequestMapping("/gokul/{id}")
    // public String gokul(@PathVariable("id") String id) {
    // return "Hello gokul your message is " + id;
    // }

    @GetMapping("/all")
    public ResponseEntity<Object> getMethodName() {
        return ResponseEntity.ok(JsonResponse.builder()
                .status(HttpStatus.ACCEPTED)
                .message("User list returned")
                .data(service.getAll()).build());
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<Object> createUser(@RequestBody User entity) {

        return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponse.builder()
                .data(service.saveCustomer(entity)).message("User created successfully").status(HttpStatus.OK)
                .build());

    }

    @PostMapping("/login")
    public ResponseEntity<Object> postMethodName(@RequestBody User entity) {
        // TODO: process POST request

        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMethodName(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.builder().data("success"));
    }

    @Transactional
    @DeleteMapping("/")
    public ResponseEntity<Object> deleteCustomerUsingMail(@RequestBody String body) throws AppException {

        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String email = json.get("email").getAsString();
        return ResponseEntity.ok(service.deleteUserByEmail(email));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> putMethodName(@PathVariable String id, @RequestBody User entity) {
        // TODO: process PUT request

        return ResponseEntity.ok("Success");
    }

}