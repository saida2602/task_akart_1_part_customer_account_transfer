package com.saida.register_customer.dto.request;

import com.saida.register_customer.domain.CurrencyType;
import com.saida.register_customer.domain.TransferType;
import com.saida.register_customer.error.ErrorMessage;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {

    @NonNull
    @DecimalMin(value = "0.0", inclusive = false, message = ErrorMessage.INVALID_AMOUNT)
    private BigDecimal amount;
    @NotBlank(message = ErrorMessage.INVALID_ACCOUNT)
    private String accountNumber;
    @NotNull(message = ErrorMessage.INVALID_CURRENCY)
    private CurrencyType currency;
    @NotNull(message = ErrorMessage.INVALID_CUSTOMER)
    private Long customerId;
    @NotNull(message = ErrorMessage.INVALID_TRANSFER_TYPE)
    private TransferType transferType;

}
