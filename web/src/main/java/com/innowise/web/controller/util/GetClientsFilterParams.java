package com.innowise.web.controller.util;

import com.innowise.web.enums.ClientSubjectStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetClientsFilterParams {

    private String name;
    private List<ClientSubjectStatus> status;
    private Integer pageSize = 5;
    private Integer pageNumber = 0;
}
