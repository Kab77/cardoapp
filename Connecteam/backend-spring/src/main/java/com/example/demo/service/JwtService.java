package com.example.demo.service;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    public String generateToken(String username) {
        return Jwts.builder()
            .setSubject(username)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    }
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(secret)
            .parseClaimsJws(token).getBody().getSubject();
    }
} 