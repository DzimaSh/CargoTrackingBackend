package com.innowise.web.exception;

public class ArgumentsNotValidException extends RuntimeException{
    public ArgumentsNotValidException(String message) {
        super(message);
    }
}
