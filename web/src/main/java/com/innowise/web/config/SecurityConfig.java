package com.innowise.web.config;

import com.innowise.web.security.jwt.JwtAuthorizationFilter;
import com.innowise.web.security.jwt.JwtUtil;
import com.innowise.web.util.ExceptionHandlingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.innowise.web.enums.Roles.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String GET_USERS_API_ENDPOINT = "/api/users";
    private final String USER_API_ENDPOINTS = "/api/users/**";
    private final String[] AUTH_ENDPOINTS = {"/api/sign-in", "/api/refresh", "/api/logout"};

    private final UserDetailsService jwtUserDetailsService;
    private final JwtUtil jwtUtil;
    private final ExceptionHandlingUtil exceptionHandlingUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/about").permitAll()
                .antMatchers(HttpMethod.POST, AUTH_ENDPOINTS).permitAll()
                .antMatchers(HttpMethod.GET, GET_USERS_API_ENDPOINT).hasAnyAuthority(ADMIN.name(), DISPATCHER.name())
                .antMatchers(USER_API_ENDPOINTS).hasAnyAuthority(ADMIN.name(), SYS_ADMIN.name())
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    exceptionHandlingUtil.sendExceptionToClient(response, authException, HttpStatus.UNAUTHORIZED);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    exceptionHandlingUtil.sendExceptionToClient(response, accessDeniedException, HttpStatus.FORBIDDEN);
                });
        http.addFilterBefore(new JwtAuthorizationFilter(jwtUtil, jwtUserDetailsService, exceptionHandlingUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}