package com.livedocs.server.webapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livedocs.server.webapp.entity.SysConfig;

@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {
    List<SysConfig> findByConfigName(String name);

    // List<SysConfig> findById(Long id);

    List<SysConfig> findAll();

}
