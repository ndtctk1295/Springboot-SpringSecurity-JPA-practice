package com.example.demo.services;

import com.example.demo.models.BlacklistToken;
import com.example.demo.repositories.BlacklistTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    @Autowired
    private JwtService jwtService;

    public void addToken(String token, boolean active) {
        BlacklistToken blacklistToken = new BlacklistToken(token, active);
        blacklistTokenRepository.save(blacklistToken);
    }

    public boolean deactivateToken(String token) {
        BlacklistToken blacklistToken = blacklistTokenRepository.findByToken(token);
        if (blacklistToken != null) {
            blacklistToken.setActive(false);
            blacklistTokenRepository.save(blacklistToken);
            return true;
        }
        return false;
    }

    public List<Map<String, String>> getActiveTokens() {
        List<BlacklistToken> activeTokens = blacklistTokenRepository.findAllActiveTokens();
        return activeTokens.stream()
                .map(token -> {
                    Map<String, String> tokenInfo = new HashMap<>();
                    tokenInfo.put("token", token.getToken());
                    tokenInfo.put("username", jwtService.getUsernameFromToken(token.getToken()));
                    return tokenInfo;
                })
                .collect(Collectors.toList());
    }

    public void deleteAllTokens() {
        blacklistTokenRepository.deleteAll();
    }
}