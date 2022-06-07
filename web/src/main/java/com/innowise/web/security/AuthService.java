package com.innowise.web.security;

import com.innowise.web.dto.auth.*;
import com.innowise.web.dto.user.request.GetUserByLoginRequest;
import com.innowise.web.dto.user.response.GetUserResponse;
import com.innowise.web.exception.JwtAuthenticationException;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.jwt.JwtUtil;
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
    private final JwtUtil jwtUtil;
    private static final Set<Integer> authorizedUserIds = new HashSet<>();

    public String authenticate(AuthRequest authRequest) {
        GetUserResponse user = feignClient.getUserByLogin(
                new GetUserByLoginRequest(authRequest.getLogin()));
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), authRequest.getPassword())
        );

        authorizedUserIds.add(user.getId());
        String accessToken = jwtUtil.buildAccessToken(user);
        String refreshToken = jwtUtil.buildRefreshToken(user);

        return resolveTokensResponse(accessToken, refreshToken);
    }

    public String refresh(RefreshJwtRequest jwtRequest) {
        String userLogin = jwtUtil.decodeLoginFromJwt(jwtRequest.getToken(), false);
        GetUserResponse user = feignClient.getUserByLogin(new GetUserByLoginRequest(userLogin));

        String accessToken, refreshToken;
        if (user.getId().equals(jwtRequest.getUserId())) {
            accessToken = jwtUtil.buildAccessToken(user);
            refreshToken = jwtUtil.buildRefreshToken(user);
        } else {
            throw new JwtAuthenticationException("User id in request and real user id aren't same");
        }

        return resolveTokensResponse(accessToken, refreshToken);
    }

    public void logout(LogoutRequest logoutRequest) {
        authorizedUserIds.remove(logoutRequest.getUserId());
    }

    public static Set<Integer> getAuthorizedUserIds() {
        return authorizedUserIds;
    }

    private String resolveTokensResponse(String accessToken, String refreshToken) {
        StringBuilder jwtResponse = new StringBuilder();
        jwtResponse
                .append(accessToken)
                .append(" ")
                .append(refreshToken);
        return jwtResponse.toString();
    }
}
