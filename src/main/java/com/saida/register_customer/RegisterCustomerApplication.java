package com.saida.register_customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.saida.register_customer.client")
public class RegisterCustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegisterCustomerApplication.class, args);
    }

}
