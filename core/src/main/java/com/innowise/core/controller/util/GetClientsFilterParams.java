package com.innowise.core.controller.util;

import com.innowise.core.entity.enums.ClientSubjectStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetClientsFilterParams {

    private String name;
    private ClientSubjectStatus status;
    private Integer pageSize = 5;
    private Integer pageNumber = 0;
}
