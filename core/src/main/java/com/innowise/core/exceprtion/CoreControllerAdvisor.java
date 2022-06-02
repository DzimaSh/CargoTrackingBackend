package com.innowise.core.exceprtion;

import com.innowise.core.dto.error.ErrorResponse;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class CoreControllerAdvisor {

    private final String UNEXPECTED_EXCEPTION_MESSAGE = "Something goes wrong... Our engineers already working on it!";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorResponse> handlePSQLException(PSQLException ex) {
        return buildResponse(HttpStatus.CONFLICT, List.of(ex.getServerErrorMessage().getDetail()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                List.of(UNEXPECTED_EXCEPTION_MESSAGE, ex.getMessage()));
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, List<String> errors) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
