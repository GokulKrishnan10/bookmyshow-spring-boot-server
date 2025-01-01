package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.scheduler.server.webapp.repository.SysConfigRepository;

@Service
public class SysConfigService {
    @Autowired
    SysConfigRepository repository;

    @Transactional
    public void updateKeys(String publicKey, String privateKey) {
        repository.updateKeys(publicKey, "jwt.secret-public-key.path");
        repository.updateKeys(privateKey, "jwt.secret-private-key.path");
    }
}
