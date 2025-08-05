package com.saida.register_customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDto {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String birthDate;
    @NotBlank
    private String gsmNumber;
    @NotBlank
    private String phoneNumber;

}