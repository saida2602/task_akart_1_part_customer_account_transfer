package com.saida.register_customer.dto.request;

import com.saida.register_customer.domain.Account;
import com.saida.register_customer.domain.Customer;

public record TransferContext(Customer customer, Account account) {}

