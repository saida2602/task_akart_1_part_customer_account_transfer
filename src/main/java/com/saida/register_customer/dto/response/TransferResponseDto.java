package com.saida.register_customer.dto.response;

import com.saida.register_customer.domain.CurrencyType;
import com.saida.register_customer.domain.TransferStatus;
import com.saida.register_customer.domain.TransferType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferResponseDto {

    private CurrencyType currency;
    private String accountNumber;
    private BigDecimal amount;
    private BigDecimal balance;
    private TransferStatus status;
    private TransferType type;
    private String customerName;
    private String customerSurname;

}
