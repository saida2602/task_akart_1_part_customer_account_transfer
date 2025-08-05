package com.saida.register_customer.config;

import com.saida.register_customer.error.ErrorMessage;
import com.saida.register_customer.util.Helper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final MessageGenerator messageGenerator;
    private final Helper helper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        String errorMessage = messageGenerator.getMessage(ErrorMessage.ACCESS_DENIED);
        helper.createErrorResponse(response, errorMessage, HttpServletResponse.SC_FORBIDDEN);

    }
}