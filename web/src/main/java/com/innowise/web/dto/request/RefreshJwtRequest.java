package com.innowise.web.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    private Integer userId;
    private String token;
}
