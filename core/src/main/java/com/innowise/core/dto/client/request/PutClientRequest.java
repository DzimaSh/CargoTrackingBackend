package com.innowise.core.dto.client.request;

import com.innowise.core.entity.enums.ClientSubjectStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutClientRequest {

    private String name;
    private ClientSubjectStatus status;

}
