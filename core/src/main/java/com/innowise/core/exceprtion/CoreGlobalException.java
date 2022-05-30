package com.innowise.core.exceprtion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoreGlobalException extends RuntimeException {

    private Integer status;
    private String message;

    public CoreGlobalException(String message) {
        super(message);
    }
}
