package com.innowise.web.security;

import com.innowise.core.dto.user.request.GetUserByLoginRequest;
import com.innowise.core.entity.user.User;
import com.innowise.web.dto.request.AuthRequest;
import com.innowise.web.dto.request.LogoutRequest;
import com.innowise.web.dto.request.RefreshJwtRequest;
import com.innowise.web.dto.response.JwtResponse;
import com.innowise.web.exception.JwtAuthenticationException;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.jwt.JwtUser;
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

    public JwtResponse authenticate(AuthRequest authRequest) {
        User user = feignClient.getUserByLogin(
                new GetUserByLoginRequest(authRequest.getLogin()));
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), authRequest.getPassword())
        );

        authorizedUserIds.add(user.getId());
        String accessToken = jwtUtil.buildAccessToken(user);
        String refreshToken = jwtUtil.buildRefreshToken(user);

        return new JwtResponse(accessToken, refreshToken);
    }

    public JwtResponse refresh(RefreshJwtRequest jwtRequest) {
        String userLogin = jwtUtil.decodeLoginFromJwt(jwtRequest.getToken(), false);
        User user = feignClient.getUserByLogin(new GetUserByLoginRequest(userLogin));

        String accessToken, refreshToken;
        if (user.getId() == jwtRequest.getUserId()) {
            accessToken = jwtUtil.buildAccessToken(user);
            refreshToken = jwtUtil.buildRefreshToken(user);
        } else {
            throw new JwtAuthenticationException("User id in request and real user id aren't same");
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
