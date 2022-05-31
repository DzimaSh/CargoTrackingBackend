package com.innowise.web.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtParams {
    private String accessTokenSecret;
    private Long accessTokenLifeTime;

    private String refreshTokenSecret;
    private Long refreshTokenLifeTime;
}
