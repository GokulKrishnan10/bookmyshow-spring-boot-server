package com.scheduler.server.webapp.services;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.components.RedisDistributedLock;

@Service
public class LockService {
    private final RedisDistributedLock redisDistributedLock;

    @Autowired
    public LockService(RedisDistributedLock redisDistributedLock) {
        this.redisDistributedLock = redisDistributedLock;
    }

    public boolean acquireLock(String lockKey, String lockValue, long lockTimeout) {
        if (redisDistributedLock.acquireLock(lockKey, lockValue, lockTimeout, TimeUnit.MINUTES)) {
            return true;
        }
        return false;
    }

    public void releaseLock(String lockKey) {
        redisDistributedLock.releaseLock(lockKey);
    }
}
