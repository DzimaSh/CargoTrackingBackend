package com.innowise.web.dto.user.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetUsersResponse {
    private List<GetUserResponse> content;
    private Long totalElements;

    public GetUsersResponse(List<GetUserResponse> content, Long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }
}