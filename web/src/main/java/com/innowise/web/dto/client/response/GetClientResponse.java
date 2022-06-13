package com.innowise.web.dto.client.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innowise.web.enums.ClientSubjectStatus;
import lombok.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetClientResponse {

    private Integer id;

    private String name;

    private ClientSubjectStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp deleteDate;

}
