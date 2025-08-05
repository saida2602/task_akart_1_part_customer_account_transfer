package com.saida.register_customer.error;

import lombok.Getter;

@Getter
public class CustomerNotFoundException extends RuntimeException{

    private final String message;

    public CustomerNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public static CustomerNotFoundException of(String message) {
        return new CustomerNotFoundException(message);
    }

}
