package com.saida.register_customer.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApplicationConstants {

    public static final String BERARER = "Bearer ";
    public static final List<String> WHITELIST = List.of("/v1/api/token/getToken");


}
