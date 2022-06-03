package com.innowise.web.exception;

import com.innowise.web.dto.error.ErrorResponse;
import com.innowise.web.util.ExceptionHandlingUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

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
        return exceptionHandlingUtil.buildErrorResponseEntity(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException ex) {
        return exceptionHandlingUtil.buildErrorResponseEntity(HttpStatus.UNAUTHORIZED, List.of("JWT is invalid"));
    }

    @ExceptionHandler(CoreGlobalException.class)
    public ResponseEntity<ErrorResponse> handleCoreException(CoreGlobalException ex) {
        return exceptionHandlingUtil.buildErrorResponseEntity(HttpStatus.resolve(ex.getStatus()), ex.getErrors());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        switch (status) {
            case NOT_FOUND:
                errors.add("Page you're looking for not found!");
                break;
            case BAD_REQUEST:
                errors.add("Arguments in request cannot be resolved.");
                break;
            case METHOD_NOT_ALLOWED:
                errors.add(ex.getMessage());
                break;
            default:
                errors.add(ExceptionHandlingUtil.UNEXPECTED_EXCEPTION_MESSAGE);
                errors.add(ex.getMessage());
                break;
        }
        return new ResponseEntity<>(
                exceptionHandlingUtil.buildErrorResponseObject(status, errors),
                status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(Exception ex) {
        return exceptionHandlingUtil.buildErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,
                List.of(ExceptionHandlingUtil.UNEXPECTED_EXCEPTION_MESSAGE, ex.getMessage()));
    }
}
