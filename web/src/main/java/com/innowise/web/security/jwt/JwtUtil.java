package com.innowise.web.security.jwt;

import com.innowise.core.entity.user.User;
import com.innowise.web.exception.JwtAuthenticationException;
import com.innowise.web.project.JwtParams;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtParams jwtParams;

    public String buildAccessToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtParams.getAccessTokenLifeTime()))
                .claim("clientId", user.getClientId() != null ? Integer.toString(user.getClientId()) : null)
                .claim("roles", user.getRoles().stream()
                        .map(role -> role.getRole().name())
                        .collect(Collectors.toList()))
                .signWith(SignatureAlgorithm.HS256, jwtParams.getAccessTokenSecret().getBytes())
                .compact();
    }

    public String buildRefreshToken(User user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtParams.getRefreshTokenLifeTime()))
                .claim("userId", Integer.toString(user.getId()))
                .signWith(SignatureAlgorithm.HS256, jwtParams.getRefreshTokenSecret().getBytes())
                .compact();
    }

    public Jws<Claims> decodeAndVerifyJWT(String token, boolean isAccessToken) {
        byte[] secret = isAccessToken ?
                jwtParams.getAccessTokenSecret().getBytes() :
                jwtParams.getRefreshTokenSecret().getBytes();
        Jws<Claims> claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        if (verifyJwt(claims))
            return claims;
        return null;
    }

    private boolean verifyJwt(Jws<Claims> claims) {
        try {
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }
}
