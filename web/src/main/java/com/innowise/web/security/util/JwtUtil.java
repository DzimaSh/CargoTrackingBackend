package com.innowise.web.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.innowise.core.entity.user.User;
import com.innowise.web.project.JwtParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtParams jwtParams;

    public String buildAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getLogin())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtParams.getAccessTokenLifeTime()))
                .withClaim("clientId", Integer.toString(user.getClientId()))
                .withClaim("roles", user.getRoles().stream()
                        .map(role -> role.getRole().name())
                        .collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(jwtParams.getAccessTokenSecret().getBytes()));
    }

    public String buildRefreshToken(User user) {
        return JWT.create()
                .withSubject(Integer.toString(user.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtParams.getRefreshTokenLifeTime()))
                .sign(Algorithm.HMAC256(jwtParams.getRefreshTokenSecret().getBytes()));
    }

    public DecodedJWT decodeJWT(String token) {
        return JWT.require(Algorithm.HMAC256(jwtParams.getRefreshTokenSecret().getBytes()))
                .build()
                .verify(token);
    }
}
