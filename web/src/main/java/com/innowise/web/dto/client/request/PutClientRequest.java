package com.innowise.web.dto.client.request;

import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PutClientRequest {

    @NotEmpty(message = "company name is required")
    @Size(max = 30, message = "company name size is too big")
    private String name;

    @NotEmpty(message = "company status is required")
    private ClientSubjectStatus status;

}
