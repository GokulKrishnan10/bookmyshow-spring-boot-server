package com.livedocs.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import com.livedocs.server.webapp.entity.Users;
import com.livedocs.server.webapp.response.JsonResponse;

import java.util.*;
import com.livedocs.server.webapp.services.UsersService;
import com.livedocs.server.webapp.validation.Validation;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private UsersService service;

    private Validation valid = new Validation();

    // Using path variable like api.example.com/gokul/name
    // @RequestMapping("/gokul/{id}")
    // public String gokul(@PathVariable("id") String id) {
    // return "Hello gokul your message is " + id;
    // }

    @GetMapping("")
    public String helloServer() {
        return "Welcome to the Spring Server";
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getMethodName() {
        return ResponseEntity.ok(JsonResponse.createResponse()
                .setStatus(HttpStatus.ACCEPTED)
                .setMessage("Users list returned")
                .setData(service.getAll()));
    }

    @PostMapping("/create/customer")
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody Users entity) {
        if (valid.validateRequestBody(entity) || service.verifyUser(entity))
            return ResponseEntity.badRequest().body("Error");
        Users cusObj = Users.create()
                .setName(entity.getName())
                .setAge(entity.getAge())
                .setCity(entity.getCity())
                .setPassword(service.getHashedPassword(entity.getPassword()))
                .setCountry(entity.getCountry())
                .setEmail(entity.getEmail());
        return ResponseEntity.ok(service.saveCustomer(cusObj));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Users user) {
        return ResponseEntity.ok(
                JsonResponse.createResponse()
                        .setMessage("success")
                        .setStatus(HttpStatus.ACCEPTED)
                        .setData(service.getJwtToken(user)));
    }

    @GetMapping("/verify")
    public String getMethodName(@RequestHeader MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(key + " and value is " + value);
        });
        List<String> tokens = headers.get("Authorization");
        System.out.println("Authorization Headers " + tokens);
        return "Done";
    }

    // Using Request Param api.example.com/delete?id=10
    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestParam Long id) {
        String message = service.deleteCustomer(id);
        return ResponseEntity
                .ok(Map.of("message", message, "status", HttpStatus.ACCEPTED));
    }

}