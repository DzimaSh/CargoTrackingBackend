package com.innowise.web.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoreGlobalException extends RuntimeException {

    private Integer status;
    private List<String> errors;

    public CoreGlobalException(String message) {
        super(message);
    }
}
