package com.innowise.web.exception;

import io.jsonwebtoken.JwtException;

public class JwtAuthenticationException extends JwtException {
    public JwtAuthenticationException(String message) {
        super(message);
    }
}
