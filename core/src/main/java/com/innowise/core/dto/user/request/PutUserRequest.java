package com.innowise.core.dto.user.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutUserRequest extends PostUserRequest {

    private Boolean isChangePassword;
}