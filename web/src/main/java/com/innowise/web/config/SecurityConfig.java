package com.innowise.web.config;

import com.innowise.web.security.CustomAuthorizationFilter;
import com.innowise.web.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.innowise.core.entity.enums.Roles.ADMIN;
import static com.innowise.core.entity.enums.Roles.DISPATCHER;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String USERS_API_ENDPOINTS = "api/users";

    private final UserDetailsService jwtUserDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/sign-in").permitAll()
                .and()
                .antMatcher(USERS_API_ENDPOINTS)
                    .authorizeRequests()
                    .antMatchers(HttpMethod.GET).hasAnyAuthority(ADMIN.name(), DISPATCHER.name())
                    .antMatchers(USERS_API_ENDPOINTS + "/**").hasRole(ADMIN.name())
                .anyRequest().authenticated();

        http.addFilterBefore(new CustomAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}