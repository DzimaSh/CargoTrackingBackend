package com.innowise.web.security.jwt;

import com.innowise.core.entity.role.Role;
import com.innowise.core.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtParams jwtParams;

    public String buildAccessToken(User user) {
        Claims claims = new DefaultClaims();
        claims.putIfAbsent("roles", getUserRolesNames(user.getRoles()));
        if (user.getClientId() != null) {
            claims.putIfAbsent("clientId", user.getClientId());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtParams.getAccessTokenLifeTime()))
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

    public String getLoginFromJwt(String token, boolean isAccessToken) {
        return decodeTokenBody(token, isAccessToken).getSubject();
    }

    public <T> T getClaimFromJwt(String token, String claimName, Class<T> type, boolean isAccessToken) {
        return decodeTokenBody(token, isAccessToken).get(claimName, type);
    }

    private DefaultClaims decodeTokenBody(String token, boolean isAccessToken) {
        byte[] secret = isAccessToken ?
                jwtParams.getAccessTokenSecret().getBytes() :
                jwtParams.getRefreshTokenSecret().getBytes();
        return (DefaultClaims) Jwts.parser().setSigningKey(secret).parse(token).getBody();
    }
    private List<String> getUserRolesNames(Set<Role> roles) {
        return roles.stream()
                .map(role -> role.getRole().name())
                .collect(Collectors.toList());
    }
}
