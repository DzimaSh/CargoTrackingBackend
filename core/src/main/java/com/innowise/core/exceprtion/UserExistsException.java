package com.innowise.core.exceprtion;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UserExistsException extends RuntimeException {

    private HttpStatus status;

    public UserExistsException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
