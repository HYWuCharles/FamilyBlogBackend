package com.haoyu.family_blog.Service.impl;

import com.haoyu.family_blog.Service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Haoyu WU on 2020/4/16.
 */
@Service
public class AuthService implements IAuthService {
    @Autowired
    private RedisService redisService;

    private static final long EXPIRATION = 60 * 15;

    @Value("${redis.auth.prefix}")
    private String authPreFix;

    @Override
    public String generateVerificationCode(String uuid) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        redisService.set(authPreFix + uuid, sb.toString());
        redisService.expire(authPreFix + uuid, EXPIRATION);
        return sb.toString();
    }

    @Override
    public boolean verifyVerificationCode(String uuid, String code) {
        String realCode = redisService.get(authPreFix + uuid);
        return realCode.equals(code);
    }

    @Override
    public void deleteVerificationCode(String uuid) {
        redisService.remove(authPreFix + uuid);
    }
}
