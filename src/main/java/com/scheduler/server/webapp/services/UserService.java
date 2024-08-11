package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.User;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.repository.UserRepository;

import java.util.*;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtService service;

    @Transactional
    public String saveCustomer(User obj) {
        userRepo.save(obj);
        return obj.getId().toString();
    }

    @Transactional
    public String deleteCustomer(Long id) throws AppException {
        try {
            userRepo.deleteById(id);
            return "User deletion Successful";
        } catch (Exception e) {
            throw AppException.builder()
                    .message("User Not Found")
                    .status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Transactional
    public String deleteUserByEmail(String mail) throws AppException {
        try {
            userRepo.deleteByEmail(mail);
            return "User deletion successful";
        } catch (Exception e) {
            throw AppException.builder()
                    .message("User Not Found")
                    .status(HttpStatus.NOT_FOUND).build();
        }
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public boolean verifyUser(User user) {
        return !userRepo.findByEmail(user.getEmail()).isEmpty();
    }

    public String getJwtToken(User user) throws AppException {
        if (!verifyUser(user)) {
            throw AppException.builder()
                    .message("User Not Found")
                    .status(HttpStatus.NOT_FOUND).build();
        }
        return service.generateJwtToken(user);
    }

    public String getHashedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(password);
    }

    public List<User> findByAgeLessThan(int age) {
        return userRepo.findAll().stream().filter(user -> (user.getAge() < age)).toList();
    }

}
