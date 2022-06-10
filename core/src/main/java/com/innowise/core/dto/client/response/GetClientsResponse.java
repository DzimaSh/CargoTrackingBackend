package com.innowise.core.dto.client.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetClientsResponse {
    private List<GetClientResponse> content;
    private Long totalElements;
}
