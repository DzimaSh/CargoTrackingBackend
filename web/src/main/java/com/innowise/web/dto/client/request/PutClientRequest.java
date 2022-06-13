package com.innowise.web.dto.client.request;

import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutClientRequest {

    private String name;
    private ClientSubjectStatus status;

}
