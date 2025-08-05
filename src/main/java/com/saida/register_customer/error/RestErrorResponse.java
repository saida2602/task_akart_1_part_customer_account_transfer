package com.saida.register_customer.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RestErrorResponse {

    private String message;
    private int code;
    private String description;

}
