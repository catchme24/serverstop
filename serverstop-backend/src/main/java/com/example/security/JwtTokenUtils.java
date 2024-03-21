package com.example.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenUtils {

    private final SecurityProperty property;

    public String generate(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + property.getTokenLifetime()))
                .signWith(SignatureAlgorithm.HS512, property.getSecretKey())
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .setSigningKey(property.getSecretKey())
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public boolean validate(String authToken) {
        try {
            Jwts.parser()
                .setSigningKey(property.getSecretKey())
                .parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.warn("JWT token is expired or invalid. Details: {}, token={}", e.getMessage(), authToken);
            return false;
        }
    }

}
