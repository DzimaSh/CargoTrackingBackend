package com.innowise.web.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RefreshJwtRequest {
    @NotNull
    private Integer userId;
    private String token;
}
