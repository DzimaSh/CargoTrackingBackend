package com.innowise.web.dto.response;

import com.innowise.core.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetAllUsersResponse {
    private List<UserResponseDTO> content;
    private int totalElements;

    public GetAllUsersResponse(List<UserResponseDTO> content) {
        this.content = content;
        totalElements = content.size();
    }
}
