package com.innowise.core.dto.client.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.innowise.core.entity.client.Client;
import com.innowise.core.entity.enums.ClientStatus;
import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetClientResponse {

    private Integer id;

    private String name;

    private ClientStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Timestamp deleteDate;

    public GetClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.status = client.getStatus();
        this.deleteDate = client.getDeleteDate();
    }
}
