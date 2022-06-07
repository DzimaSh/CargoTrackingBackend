package com.innowise.web.dto.error;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class ErrorResponse {
    @Builder.Default
    private Date timestamp = new Date();
    private Integer status;
    private List<String> errors;
}