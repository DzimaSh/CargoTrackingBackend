package com.innowise.core.exceprtion;

import com.innowise.core.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CoreControllerAdvisor {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExistsException(UserExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, List.of(ex.getMessage()));
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, List<String> errors) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
