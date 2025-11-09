package com.scheduler.server.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scheduler.server.webapp.entity.JobMapping;
import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.services.JobMapperService;

@RequestMapping("/api/job")
public class JobController {

    @Autowired
    JobMapperService service;

    @PostMapping
    public ResponseEntity<Object> insertJobMapping(JobMapping appJob) {
        service.insertMapper(appJob);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/schedule")
    public ResponseEntity<Object> scheduleJob(Job job) {
        return ResponseEntity.ok("Job scheduled successfully");
    }
}
