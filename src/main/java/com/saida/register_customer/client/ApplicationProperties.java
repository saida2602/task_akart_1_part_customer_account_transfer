package com.saida.register_customer.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "services.akart")
public class ApplicationProperties {

    private String username;
    private String password;

}