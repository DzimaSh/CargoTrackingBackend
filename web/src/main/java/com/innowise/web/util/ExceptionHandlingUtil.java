package com.innowise.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.core.dto.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ExceptionHandlingUtil {

    private final ObjectMapper objectMapper;

    public void sendExceptionToClient(HttpServletResponse response,
                                      Exception ex, HttpStatus status) throws IOException {
        int statusNum = status.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(statusNum)
                .errors(List.of(ex.getMessage()))
                .build();

        response.setContentType("application/json");
        response.setStatus(statusNum);
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    public ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, List<String> errors) {
        ErrorResponse response = ErrorResponse.builder()
                .status(status.value())
                .errors(errors)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
