package com.innowise.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthRequest {
    @NotBlank
    private String login;
    @NotNull
    private String password;
}
