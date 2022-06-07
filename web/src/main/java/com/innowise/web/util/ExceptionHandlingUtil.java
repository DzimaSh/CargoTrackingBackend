package com.innowise.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.web.dto.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ExceptionHandlingUtil {

    public static final String UNEXPECTED_EXCEPTION_MESSAGE = "Something goes wrong... Our engineers already working on it!";

    private final ObjectMapper objectMapper;

    public void sendExceptionToClient(HttpServletResponse response,
                                      Exception ex, HttpStatus status) throws IOException {
        int statusNum = status.value();
        List<String> errors = new ArrayList<>();
        if (statusNum == 500)
            errors.add(UNEXPECTED_EXCEPTION_MESSAGE);
        errors.add(ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(statusNum)
                .errors(errors)
                .build();

        response.setContentType("application/json");
        response.setStatus(statusNum);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    public ResponseEntity<ErrorResponse> buildErrorResponseEntity(HttpStatus status, List<String> errors) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, status);
    }

    public ErrorResponse buildErrorResponseObject(HttpStatus status, List<String> errors) {
        return ErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .build();
    }
}
