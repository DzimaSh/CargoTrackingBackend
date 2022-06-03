package com.innowise.web.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.web.exception.CoreGlobalException;
import com.innowise.web.util.ExceptionHandlingUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ExceptionHandlingUtil exceptionHandlingUtil;

    @Override
    public Exception decode(String methodKey, Response response) {
        CoreGlobalException exceptionMessage = null;

        try (Reader reader = response.body().asReader(StandardCharsets.UTF_8)) {
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            exceptionMessage = mapper.readValue(result,
                    CoreGlobalException.class);
            exceptionMessage.setStatus(response.status());
        } catch (IOException ignored) {

        }
        return exceptionMessage;
    }
}
