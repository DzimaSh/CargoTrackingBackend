package com.innowise.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LogoutRequest {
    @NotNull
    private Integer userId;
}
