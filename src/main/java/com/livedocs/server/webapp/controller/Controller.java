package com.livedocs.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import com.livedocs.server.webapp.entity.User;
import com.livedocs.server.webapp.response.JsonResponse;

import java.util.*;

import com.livedocs.server.webapp.services.JwtService;
import com.livedocs.server.webapp.services.UserService;

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
    private UserService service;
    @Autowired
    private JwtService jwtService;

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
                .setMessage("User list returned")
                .setData(service.getAll()));
    }

    @PostMapping("/customer")
    @Transactional
    public ResponseEntity<Object> createUser(@RequestBody User entity) {
        User user = User.create()
                .setName(entity.getName())
                .setAge(entity.getAge())
                .setDOB(entity.getDOB())
                .setPhoneNumber(entity.getPhoneNumber())
                .setCountry(entity.getCountry())
                .setEmail(entity.getEmail())
                .setPassword(service.getHashedPassword(entity.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(JsonResponse.createResponse()
                .setData(service.saveCustomer(user)).setMessage("User created successfully").setStatus(HttpStatus.OK));
    }

    @DeleteMapping("/customer")
    @Transactional
    public ResponseEntity<Object> deleteCustomerUsingMail(@RequestBody String mail) {
        return ResponseEntity.ok(service.deleteUserByEmail(mail));
    }

    @PostMapping("/jwttoken")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Object response = JsonResponse.createResponse()
                .setMessage("success")
                .setStatus(HttpStatus.ACCEPTED)
                .setData(service.getJwtToken(user));
        return ResponseEntity.ok(response);
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
    public ResponseEntity<Object> deleteUser(@RequestParam Long id,
            @RequestHeader MultiValueMap<String, String> headers) {
        List<String> tokens = headers.get("Authorization");
        if (jwtService.verifyJwtToken(tokens.get(0))) {
            String message = service.deleteCustomer(id);
            return ResponseEntity
                    .ok(Map.of("message", message, "status", HttpStatus.ACCEPTED));

        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(JsonResponse.createResponse()
                .setStatus(HttpStatus.UNAUTHORIZED).setMessage("Unauthorized user").setData("Null"));
    }

}