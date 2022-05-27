package com.innowise.web.security;

import com.innowise.core.entity.user.User;
import com.innowise.web.dto.request.AuthRequest;
import com.innowise.web.dto.request.LogoutRequest;
import com.innowise.web.dto.request.RefreshJwtRequest;
import com.innowise.web.dto.response.JwtResponse;
import com.innowise.web.exception.JwtAuthenticationException;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.jwt.JwtUser;
import com.innowise.web.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFeignClient feignClient;
    private final AuthenticationManager authManager;
    private final JwtUtil tokenBuilder;
    private static final Set<Integer> authorizedUserIds = new HashSet<>();

    public JwtResponse authenticate(AuthRequest authRequest) {
        String login = authRequest.getLogin();
        User user = feignClient.getUserByLogin(login);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), authRequest.getPassword())
        );

        authorizedUserIds.add(user.getId());
        String accessToken = tokenBuilder.buildAccessToken(user);
        String refreshToken = tokenBuilder.buildRefreshToken(user);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refresh(RefreshJwtRequest jwtRequest) {
        Jws<Claims> claims = tokenBuilder.decodeAndVerifyJWT(jwtRequest.getToken(), false);
        User user = feignClient.getUserByLogin(claims.getBody().getSubject());

        String accessToken, refreshToken;
        if (user.getId() == jwtRequest.getUserId()) {
            accessToken = tokenBuilder.buildAccessToken(user);
            refreshToken = tokenBuilder.buildRefreshToken(user);
        } else {
            throw new JwtAuthenticationException("ser id in request and real user id aren't same");
        }

        return new JwtResponse(accessToken, refreshToken);
    }

    public void logout(LogoutRequest logoutRequest, JwtUser user) {
        if (logoutRequest.getUserId() == user.getId()) {
            authorizedUserIds.remove(logoutRequest.getUserId());
        } else {
            throw new JwtAuthenticationException("User id in request and real user id aren't same");
        }
    }

    public static Set<Integer> getAuthorizedUserIds() {
        return authorizedUserIds;
    }
}
