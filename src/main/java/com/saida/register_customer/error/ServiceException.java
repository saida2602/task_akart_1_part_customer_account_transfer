package com.saida.register_customer.error;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

    private final String message;

    public ServiceException(String message) {
        super(message);
        this.message = message;
    }

    public static ServiceException of(String message) {
        return new ServiceException(message);
    }

}
