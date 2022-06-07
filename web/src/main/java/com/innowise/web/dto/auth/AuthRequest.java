package com.innowise.web.dto.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthRequest {
    @NotBlank
    @Size(min = 5, max = 15)
    private String login;
    @NotBlank
    @Size(min = 5, max = 25)
    private String password;
}
