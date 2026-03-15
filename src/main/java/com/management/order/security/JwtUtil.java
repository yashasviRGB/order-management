package com.management.order.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET = "secret-key-123456";

    private final Algorithm algorithm = Algorithm.HMAC256(SECRET);

    public String generateToken(String username, String role) {

        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }

    public String extractUsername(String token) {

        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);

        return jwt.getSubject();
    }

    public String extractRole(String token) {

        DecodedJWT jwt = JWT.require(algorithm)
                .build()
                .verify(token);

        return jwt.getClaim("role").asString();
    }

    public boolean validateToken(String token) {

        try {
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}