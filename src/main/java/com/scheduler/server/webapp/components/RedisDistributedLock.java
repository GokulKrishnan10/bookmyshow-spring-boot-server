package com.scheduler.server.webapp.components;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDistributedLock {
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean acquireLock(String lockKey, String lockValue, long lockTimeout, TimeUnit unit) {
        Object value = this.redisTemplate.opsForValue().get(lockKey);
        if (value != null && "expired".equals(value.toString())) {
            return false;
        }
        return this.redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, lockTimeout, unit);
    }

    public void releaseLock(String lockKey) {
        this.redisTemplate.opsForValue().set(lockKey, "expired", 24, TimeUnit.HOURS);
    }
}
