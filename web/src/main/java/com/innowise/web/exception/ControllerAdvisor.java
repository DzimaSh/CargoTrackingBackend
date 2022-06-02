package com.innowise.web.exception;

import com.innowise.core.dto.error.ErrorResponse;
import com.innowise.web.util.ExceptionHandlingUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    private final ExceptionHandlingUtil exceptionHandlingUtil;

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
        return exceptionHandlingUtil.buildResponse(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        return exceptionHandlingUtil.buildResponse(HttpStatus.FORBIDDEN, List.of(ex.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CoreGlobalException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreGlobalException ex) {
        return exceptionHandlingUtil.buildResponse(HttpStatus.resolve(ex.getStatus()), ex.getErrors());
    }

}
