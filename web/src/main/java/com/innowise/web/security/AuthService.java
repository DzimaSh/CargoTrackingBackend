package com.innowise.web.security;

import com.innowise.core.entity.user.User;
import com.innowise.web.dto.request.AuthRequest;
import com.innowise.web.dto.response.JwtResponse;
import com.innowise.web.feign.UserFeignClient;
import com.innowise.web.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserFeignClient feignClient;
    private final AuthenticationManager authManager;
    private final JwtUtil tokenBuilder;

    public JwtResponse authenticate(AuthRequest authRequest) {
        String login = authRequest.getLogin();
        User user = feignClient.getUserByLogin(login);
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getLogin(), authRequest.getPassword())
        );

        String accessToken = tokenBuilder.buildAccessToken(user);
        String refreshToken = tokenBuilder.buildRefreshToken(user);

        return new JwtResponse(accessToken, refreshToken);
    }
}
