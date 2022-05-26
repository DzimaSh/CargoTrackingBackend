package com.innowise.web.exception;

public class JwtAuthenticationException extends RuntimeException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
