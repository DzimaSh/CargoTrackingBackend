package com.innowise.core.dto.user.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUsersResponse {
    private List<GetUserByIdResponse> content;
    private Long totalElements;

    public GetUsersResponse(List<GetUserByIdResponse> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }
}