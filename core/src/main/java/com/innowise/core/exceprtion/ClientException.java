package com.innowise.core.exceprtion;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ClientException extends RuntimeException {

    private HttpStatus status;

    public ClientException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
