package com.saida.register_customer.mapper;

import com.saida.register_customer.domain.Account;
import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.dto.request.AccountDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "customerId", source = "account.customer.id")
    AccountDto toAccountDto(Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customer")
    Account toAccount(AccountDto accountDto, Customer customer);

    @Mapping(target = "id", ignore = true)
    void updateAccount(AccountDto dto, @MappingTarget Account account);

    default void updateAccountWithCustomer(AccountDto dto, Account account, Customer customer) {
        updateAccount(dto, account);
        account.setCustomer(customer);
    }

    List<AccountDto> toAccountsDto(List<Account> accounts);
}
