package com.scheduler.server.webapp.services;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.entity.User;
import com.scheduler.server.webapp.exception.AppException;
import com.scheduler.server.webapp.repository.UserRepository;

import java.util.List;

import jakarta.transaction.Transactional;

import com.scheduler.server.webapp.dto.UserDto;
import com.scheduler.server.webapp.mappers.UserMapper;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final JwtService service;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepo = userRepository;
        this.service = jwtService;
        this.encoder = new BCryptPasswordEncoder(16);
    }

    @Transactional
    public String saveCustomer(User entity) {
        User user = User.builder()
                .name(entity.getName())
                .dob(entity.getDob())
                .phoneNumber(entity.getPhoneNumber())
                .country(entity.getCountry())
                .email(entity.getEmail())
                .role(entity.getRole())
                .password(getHashedPassword(entity.getPassword()))
                .build();
        userRepo.save(user);
        return user.getId().toString();
    }

    public User getUserByUserName(String mail) {
        return userRepo.findByEmail(mail).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Transactional
    public String deleteCustomer(Long id) throws AppException {
        try {
            userRepo.deleteById(id);
            return "User deletion Successful";
        } catch (Exception e) {
            throw AppException.builder()
                    .message("User Not Found: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @Transactional
    public String deleteUserByEmail(String mail) throws AppException {
        try {
            userRepo.deleteByEmail(mail);
            return "User deletion successful";
        } catch (Exception e) {
            throw AppException.builder()
                    .message("User Not Found: " + e.getMessage())
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    public List<UserDto> getAll() {
        List<User> users = userRepo.findAll();
        return UserMapper.INSTANCE.toUserDtoList(users);
    }

    public boolean verifyUser(User user) {
        return !userRepo.findByEmail(user.getEmail()).isEmpty();
    }

    public UserDto getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("specified user not found"));
        return UserMapper.INSTANCE.toUserDto(user);
    }

    public String getJwtToken(User user) throws AppException {
        if (!verifyUser(user)) {
            throw AppException.builder()
                    .message("User Not Found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return service.generateJwtToken(user);
    }

    public String getHashedPassword(String password) {
        return encoder.encode(password);
    }
}
