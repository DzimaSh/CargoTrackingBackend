package com.innowise.web.dto.client.request;

import com.innowise.web.dto.user.request.PostUserRequest;
import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClientRequest {
    private String name;
    private ClientSubjectStatus status;
    private PostUserRequest adminInfo;
}
