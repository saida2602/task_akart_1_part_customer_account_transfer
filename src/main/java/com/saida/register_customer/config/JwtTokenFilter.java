package com.saida.register_customer.config;

import com.saida.register_customer.error.ErrorMessage;
import com.saida.register_customer.util.Helper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

import static com.saida.register_customer.constant.ApplicationConstants.BERARER;
import static com.saida.register_customer.constant.ApplicationConstants.WHITELIST;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenOperation jwtTokenOperation;
    private final MessageGenerator messageGenerator;
    private final Helper helper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.nonNull(bearerToken) && bearerToken.startsWith(BERARER)) {
            String token = bearerToken.substring(BERARER.length()).trim();
            try {
                Claims claims = jwtTokenOperation.parseToken(token);
                Authentication authentication = jwtTokenOperation.getAuthenticatione(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                String errorMessage = messageGenerator.getMessage(ErrorMessage.EXPIRED_TOKEN);
                helper.createErrorResponse(response, errorMessage, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } catch (JwtException e) {
                String errorMessage = messageGenerator.getMessage(ErrorMessage.INVALID_TOKEN);
                helper.createErrorResponse(response, errorMessage, HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            String errorMessage = messageGenerator.getMessage(ErrorMessage.AUTHORISATION_NULL);
            helper.createErrorResponse(response, errorMessage, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return WHITELIST.contains(path);
    }

}