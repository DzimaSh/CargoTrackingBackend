package com.innowise.web.dto.client.request;

import com.innowise.web.dto.user.request.PostUserRequest;
import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClientRequest {

    @NotBlank(message = "company name is required")
    @Size(max = 30, message = "company name size is too big")
    private String name;

    @NotNull(message = "company status is required")
    private ClientSubjectStatus status;

    @Valid
    @NotNull(message = "please input admin user information")
    private PostUserRequest adminInfo;
}
