package com.saida.register_customer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saida.register_customer.error.RestErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Helper {

    private final ObjectMapper objectMapper;

    public void createErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
        RestErrorResponse error = new RestErrorResponse(message, statusCode, UUID.randomUUID().toString());
        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }
}