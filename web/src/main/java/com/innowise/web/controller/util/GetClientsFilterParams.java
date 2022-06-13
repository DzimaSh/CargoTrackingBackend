package com.innowise.web.controller.util;

import com.innowise.web.enums.ClientSubjectStatus;
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
