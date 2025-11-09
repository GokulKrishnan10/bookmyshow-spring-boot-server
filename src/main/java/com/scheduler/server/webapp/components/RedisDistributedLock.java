package com.scheduler.server.webapp.components;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDistributedLock {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean acquireLock(String lockKey, String lockValue, long lockTimeout, TimeUnit unit) {
        Object value = redisTemplate.opsForValue().get(lockKey);
        if ("expired".equals(value)) {
            return false;
        }
        return redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, lockTimeout, unit);
    }

    public void releaseLock(String lockKey) {
        redisTemplate.opsForValue().set(lockKey, "expired", 24, TimeUnit.HOURS);
    }
}
