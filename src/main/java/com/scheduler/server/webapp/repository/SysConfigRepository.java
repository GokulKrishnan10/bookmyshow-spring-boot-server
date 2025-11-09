package com.scheduler.server.webapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.scheduler.server.webapp.entity.SysConfig;

@Repository
public interface SysConfigRepository extends JpaRepository<SysConfig, Long> {

    List<SysConfig> findByConfigName(String name);

    @Modifying
    @Query("update SysConfig set configValue = ?1 where configName = ?2")
    void updateKeys(String publicKey, String configName);

    List<SysConfig> findAll();
}
