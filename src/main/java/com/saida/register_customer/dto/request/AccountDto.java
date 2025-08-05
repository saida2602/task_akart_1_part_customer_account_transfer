package com.saida.register_customer.dto.request;

import com.saida.register_customer.domain.AccountStatus;
import com.saida.register_customer.domain.CurrencyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    @NotBlank
    private String accountNumber;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private CurrencyType currencyType;
    @NotNull
    private AccountStatus status;
    @NotNull
    private Long customerId;

}
