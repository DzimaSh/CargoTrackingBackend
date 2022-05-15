package com.innowise.web.dto.response;

import com.innowise.core.dto.user.UserResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Predicate;

@Getter
@Setter
public class GetAllUsersResponse {
    private List<UserResponseDTO> content;
    private Long totalElements;

    public GetAllUsersResponse(List<UserResponseDTO> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }
}
