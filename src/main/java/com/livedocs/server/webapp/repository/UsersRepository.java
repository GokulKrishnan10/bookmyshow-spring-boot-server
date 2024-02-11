package com.livedocs.server.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livedocs.server.webapp.entity.Users;
import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByAge(int age);

    List<Users> findByCountry(String country);

    List<Users> findByEmail(String email);

    void deleteById(Long id);
}
