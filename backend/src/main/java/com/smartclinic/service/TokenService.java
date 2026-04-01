package com.smartclinic.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * TokenService handles JWT token generation and validation.
 * It is responsible for authenticating users securely.
 */
@Service
public class TokenService {

    /**
     * Secret key used for signing JWT tokens.
     * This should be securely stored in application properties.
     */
    @Value("${security.jwt.secret}")
    private String jwtSecret;

    /**
     * Generate JWT token for a given email
     *
     * @param email user's email
     * @return generated JWT token
     */
    public String generateToken(String email) {
        Date now = new Date();

        // Token expiry time (8 hours)
        Date expiry = new Date(now.getTime() + 1000L * 60 * 60 * 8);

        try {
            return Jwts.builder()
                    .setSubject(email)
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (Exception e) {
            // Handle token generation failure
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Generate signing key using secret
     *
     * @return Key used for signing JWT
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Validate JWT token
     *
     * @param token JWT token
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            // Invalid token or expired
            return false;
        }
    }
}