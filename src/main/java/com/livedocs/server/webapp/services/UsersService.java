package com.livedocs.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import jakarta.transaction.Transactional;
import com.livedocs.server.webapp.entity.Users;
import com.livedocs.server.webapp.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository userRepo;
    @Autowired
    private JwtService service;

    @Transactional
    public String saveCustomer(Users obj) {
        userRepo.save(obj);
        return obj.getId().toString();
    }

    @Transactional
    public String deleteCustomer(Long id) {
        try {
            userRepo.deleteById(id);
            return "User deletion Successful";
        } catch (Exception e) {
            return "User does not present";
        }
    }

    @Transactional
    public String deleteUserByEmail(String mail) {
        try {
            userRepo.deleteByEmail(mail);
            return "User deletion successful";
        } catch (Exception e) {
            return "User not Found";
        }
    }

    public List<Users> getAll() {
        return userRepo.findAll();
    }

    public boolean verifyUser(Users user) {
        return !userRepo.findByEmail(user.getEmail()).isEmpty();
    }

    public String getJwtToken(Users user) {
        if (!verifyUser(user))
            return "User, does Not exist";
        return service.generateJwtToken(user);
    }

    public String getHashedPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        return encoder.encode(password);
    }

    public List<Users> findByAgeLessThan(int age) {
        return userRepo.findAll().stream().filter(user -> (user.getAge() < age)).toList();
    }

}
