//package com.example.demo.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RedisService {
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    public RedisService(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }
//
//    public void addToBlacklist(String token) {
//        stringRedisTemplate.opsForValue().set(token, "blacklisted");
//    }
//
//    public boolean isBlacklisted(String token) {
//        return stringRedisTemplate.hasKey(token);
//    }
//}
//
