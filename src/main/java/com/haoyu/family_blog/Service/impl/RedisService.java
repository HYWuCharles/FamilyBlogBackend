package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Service.IRedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
@Service
public class RedisService implements IRedisService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean expire(String key, long exp) {
        return redisTemplate.expire(key, exp, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public void remove(Long id) {
        redisTemplate.delete(id);
    }

    @Override
    public void setAllHash(String key, HashMap<String, Object> hashMap) {
        redisTemplate.opsForHash().putAll(key, hashMap);
    }

    @Override
    public void setSet(String userName, String value) {
        redisTemplate.opsForSet().add(userName, value);
    }

    @Override
    public Set<String> getSet(String userName) {
        return redisTemplate.opsForSet().members(userName);
    }

    @Override
    public boolean isEmptySet(String userName) {
        return redisTemplate.opsForSet().size(userName) == 0;
    }

    @Override
    public void deleteSetElement(String userName, String value) {
        redisTemplate.opsForSet().remove(userName, value);
    }

    @Override
    public void setSingleHashValue(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public HashMap<String, Object> getAllHash(String key) {
        return (HashMap) redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void deleteHash(String key) {
        for (String k : getAllHash(key).keySet()) {
            redisTemplate.opsForHash().delete(key, k);
        }
        redisTemplate.delete(key);
    }

    @Override
    public boolean hasHashMap(String key) {
        return redisTemplate.hasKey(key);
    }
}
