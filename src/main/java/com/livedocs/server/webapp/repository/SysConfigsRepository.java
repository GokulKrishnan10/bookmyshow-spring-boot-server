package com.livedocs.server.webapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.livedocs.server.webapp.entity.SysConfigs;

@Repository
public interface SysConfigsRepository extends JpaRepository<SysConfigs, Long> {
    List<SysConfigs> findByConfigName(String name);

    // List<SysConfigs> findById(Long id);

    List<SysConfigs> findAll();

}
