package com.innowise.web.exception;

import com.innowise.core.dto.error.ErrorResponse;
import com.innowise.core.exceprtion.CoreGlobalException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(ValidationException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            StringBuilder builder = new StringBuilder();
            builder
                    .append(((FieldError) error).getField())
                    .append(" ")
                    .append(error.getDefaultMessage());
            errors.add(builder.toString());
        });

        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CoreGlobalException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreGlobalException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getStatus())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.resolve(ex.getStatus()));
    }

}
