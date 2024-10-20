package com.scheduler.server.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scheduler.server.webapp.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByCountry(String country);

    List<User> findByEmail(String email);

    void deleteById(Long id);

    void deleteByEmail(String email);
}
