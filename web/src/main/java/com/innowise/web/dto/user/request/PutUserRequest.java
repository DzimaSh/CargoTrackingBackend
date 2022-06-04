package com.innowise.web.dto.user.request;

import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutUserRequest extends PostUserRequest{

    @NotNull
    private Boolean isChangePassword;
}