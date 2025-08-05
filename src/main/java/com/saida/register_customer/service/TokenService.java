package com.saida.register_customer.service;

import com.saida.register_customer.client.AkartClient;
import com.saida.register_customer.dto.request.UserRequestDto;
import com.saida.register_customer.dto.response.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final AkartClient akartClient;

    public TokenDto getToken(UserRequestDto userRequestDto) {
        return akartClient.getToken(userRequestDto);

    }
}
