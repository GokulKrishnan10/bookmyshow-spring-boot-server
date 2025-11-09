package com.scheduler.server.webapp.services;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.scheduler.server.webapp.components.RedisDistributedLock;

@Service
public class LockService {
    private final RedisDistributedLock redisDistributedLock;

    public LockService(RedisDistributedLock redisDistributedLock) {
        this.redisDistributedLock = redisDistributedLock;
    }

    public boolean acquireLock(String lockKey, String lockValue, long lockTimeout) {
        return redisDistributedLock.acquireLock(lockKey, lockValue, lockTimeout, TimeUnit.MINUTES);
    }

    public void releaseLock(String lockKey) {
        redisDistributedLock.releaseLock(lockKey);
    }
}
