package com.innowise.web.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final String SIGN_IN_ENDPOINT = "/api/sign-in";
    private final String REFRESH_ENDPOINT = "/api/refresh";
    private final String BEARER_PREFIX = "Bearer ";

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String path = request.getServletPath();
        if (path.equals(SIGN_IN_ENDPOINT) || path.equals(REFRESH_ENDPOINT)) {
            filterChain.doFilter(request, response);
        } else {
            final String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {

                String token = authorizationHeader.substring(BEARER_PREFIX.length());
                Jws<Claims> decodedJWT = jwtUtil.decodeAndVerifyJWT(token);

                String login = decodedJWT.getBody().getSubject();
                List<String> roles = decodedJWT.getBody().get("roles", ArrayList.class);

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                roles.forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(login, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
