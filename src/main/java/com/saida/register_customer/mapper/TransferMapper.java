package com.saida.register_customer.mapper;

import com.saida.register_customer.domain.Account;
import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.domain.Transfer;
import com.saida.register_customer.domain.TransferStatus;
import com.saida.register_customer.dto.request.AccountDto;
import com.saida.register_customer.dto.request.TransferRequestDto;
import com.saida.register_customer.dto.response.TransferResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,imports = {TransferStatus.class})
public interface TransferMapper {

    @Mapping(target="customerId",source = "account.customer.id")
    AccountDto toAccountDto(Account account);

    @Mapping(target = "customerSurname",source="transfer.customer.surname")
    @Mapping(target = "customerName",source="transfer.customer.name")
    @Mapping(target = "balance",source="account.balance")
    @Mapping(target = "amount",source="transfer.amount")
    @Mapping(target = "currency",source="transfer.currency")
    @Mapping(target = "accountNumber",source="account.accountNumber")
    @Mapping(target = "type",source="transfer.type")
    @Mapping(target = "status",source="transfer.status")
    TransferResponseDto toTransferResponseDto(Transfer transfer,Account account);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(TransferStatus.INITIAL)")
    @Mapping(target = "type", source = "transferRequestDto.transferType")
    @Mapping(target = "customer", source = "customer")
    @Mapping(target = "currency", source = "transferRequestDto.currency")
    @Mapping(target = "amount", source = "transferRequestDto.amount")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "accountNumber", source = "account.accountNumber")
    Transfer toTranasfer(Customer customer, TransferRequestDto transferRequestDto,Account account);

    @Mapping(target = "customerSurname",source="customer.surname")
    @Mapping(target = "customerName",source="customer.name")
    @Mapping(target = "balance",source="account.balance")
    @Mapping(target = "amount",source="transfer.amount")
    @Mapping(target = "currency",source="transfer.currency")
    @Mapping(target = "accountNumber",source="account.accountNumber")
    @Mapping(target = "type",source="transfer.type")
    @Mapping(target = "status",source="transfer.status")
    TransferResponseDto toTransferResponseDto(Transfer transfer,Account account,Customer customer);

}
