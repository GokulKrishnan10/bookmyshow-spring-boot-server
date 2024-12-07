package com.scheduler.server.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;

import com.scheduler.server.webapp.entity.Job;
import com.scheduler.server.webapp.entity.RowLock;
import com.scheduler.server.webapp.repository.RowLockRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class RowLockService {
    @Autowired
    RowLockRepository repository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void lockJob(Job job) {
        this.repository.lockRecord(job.getId());
    }

    public boolean checkIfLocked(Long jobId) {
        RowLock lock = this.repository.findByJobId(jobId);
        return lock != null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeLock(Long jobId) {
        this.repository.deleteByJobId(jobId);
    }
}
