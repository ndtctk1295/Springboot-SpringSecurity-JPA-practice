package com.example.demo.services;

import com.example.demo.models.Customer;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationTime;

    @Autowired
    private CustomerService customerService;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
    }

    public String generateAccessToken(String username) {
        Customer customer = customerService.findByUsername(username);
        String phoneNumber = customer.getPhoneNumber();
        return Jwts.builder()
                .setSubject(username)
                .claim("phoneNumber", phoneNumber)

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Customer customer = customerService.findByUsername(username);
        String phoneNumber = customer.getPhoneNumber();
        long refreshExpirationTime = expirationTime * 10;
        return Jwts.builder()
                .setSubject(username)
                .claim("phoneNumber", phoneNumber)

                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ... (other parts of the service)

    public String refreshExpiredToken(String token) {
        Claims claims = null;
        try {
            // Attempt to parse the token which will throw an ExpiredJwtException if the token is expired
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            // If the token is expired, we catch the exception and proceed
            claims = e.getClaims();
        } catch (JwtException | IllegalArgumentException e) {
            // If there is any other JWT parsing error, return null
            return null;
        }
        // If claims is null, it means token was not expired, so return null or handle accordingly
        if (claims == null) {
            return null;
        }
        // Extract the username from the expired token
        String username = claims.getSubject();
        // Generate a new refresh token for the username
        return generateRefreshToken(username);
    }

// ... (other parts of the service)

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public String getPhoneNumberFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("phoneNumber", String.class);
    }
}