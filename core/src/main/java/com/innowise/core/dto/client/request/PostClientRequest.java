package com.innowise.core.dto.client.request;

import com.innowise.core.dto.user.request.PostUserRequest;
import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.enums.ClientStatus;
import com.innowise.core.entity.enums.Roles;
import com.innowise.core.validation.annotations.UserOnlyWithRole;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostClientRequest {

    private String name;

    private ClientStatus status;

    @UserOnlyWithRole(role = Roles.ADMIN)
    private PostUserRequest adminInfo;

    public Client buildClientFromRequest() {
        return Client.builder()
                .name(this.name)
                .status(this.status)
                .adminInfo(this.adminInfo.buildUser())
                .deleteDate(new Timestamp(0))
                .build();
    }
}
