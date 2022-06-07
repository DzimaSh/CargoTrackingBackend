package com.innowise.web.dto.user.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserByLoginRequest {
    private String login;
}
