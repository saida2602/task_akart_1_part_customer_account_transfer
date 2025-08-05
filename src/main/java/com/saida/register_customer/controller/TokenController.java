package com.saida.register_customer.controller;

import com.saida.register_customer.dto.request.UserRequestDto;
import com.saida.register_customer.dto.response.TokenDto;
import com.saida.register_customer.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/getToken")
    public TokenDto getToken(@RequestBody UserRequestDto userRequestDto, HttpServletRequest request) {
        return tokenService.getToken(userRequestDto);
    }
}
