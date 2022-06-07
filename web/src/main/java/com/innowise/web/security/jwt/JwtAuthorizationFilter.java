package com.innowise.web.security.jwt;

import com.innowise.web.exception.JwtAuthenticationException;
import com.innowise.web.security.AuthService;
import com.innowise.web.util.ExceptionHandlingUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService jwtUserDetailsService;
    private final ExceptionHandlingUtil exceptionHandlingUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        final String BEARER_PREFIX = "Bearer ";
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            try {
                String token = authorizationHeader.substring(BEARER_PREFIX.length());

                String login = jwtUtil.decodeLoginFromJwt(token, true);
                JwtUser user = (JwtUser) jwtUserDetailsService.loadUserByUsername(login);
                if (!AuthService.getAuthorizedUserIds().contains(user.getId())) {
                    throw new JwtAuthenticationException("user is logged out");
                }
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (JwtException ex) {
                exceptionHandlingUtil.sendExceptionToClient(response, ex, HttpStatus.UNAUTHORIZED);
                return;
            } catch (Exception ex) {
                exceptionHandlingUtil.sendExceptionToClient(response, ex, HttpStatus.INTERNAL_SERVER_ERROR);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
