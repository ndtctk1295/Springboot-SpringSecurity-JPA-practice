//package com.example.demo.services;
//
//import org.springframework.stereotype.Service;
//
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Service
//public class JwtBlacklistService {
//    private final Set<String> blacklist = ConcurrentHashMap.newKeySet();
//
//    public void blacklistToken(String token) {
//        blacklist.add(token);
//    }
//
//    public boolean isBlacklisted(String token) {
//        return blacklist.contains(token);
//    }
//
//    public void removeExpiredTokens() {
//        // Logic to remove expired tokens from the blacklist
//    }
//}
