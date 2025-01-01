package com.scheduler.server.webapp.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.repository.SysConfigRepository;

@Component
public class PreTaskInitializers implements CommandLineRunner {

    @Autowired
    SysConfigRepository configRepo;

    @Override
    public void run(String... args) throws Exception {

    }

    public void seedJobs() {

    }

}
