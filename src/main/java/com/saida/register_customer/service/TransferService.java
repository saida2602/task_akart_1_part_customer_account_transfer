package com.saida.register_customer.service;

import com.saida.register_customer.config.MessageGenerator;
import com.saida.register_customer.domain.*;
import com.saida.register_customer.dto.request.TransferContext;
import com.saida.register_customer.dto.request.TransferRequestDto;
import com.saida.register_customer.dto.response.TransferResponseDto;
import com.saida.register_customer.error.ErrorMessage;
import com.saida.register_customer.error.ServiceException;
import com.saida.register_customer.mapper.TransferMapper;
import com.saida.register_customer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {
    private final TransferRepository transferRepository;
    private final TransferMapper transferMapper;
    private final CustomerService registerCustomerService;
    private final AccountService accountService;
    private final MessageGenerator messageGenerator;


    public TransferResponseDto transfer(TransferRequestDto requestDto) {
        TransferContext context = prepareTransferContext(requestDto);
        Customer customer = context.customer();
        Account account = context.account();
        Transfer transfer = transferMapper.toTranasfer(customer, requestDto, account);
        transferRepository.save(transfer);
        makeTransfer(transfer, account, requestDto);
        return transferMapper.toTransferResponseDto(transfer, account);
    }

    private void checkAccountStatus(Account account) {
        if (account.getStatus().equals(AccountStatus.CLOSED)) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.INVALID_ACCOUNT));
        }
    }

    private void makeTransfer(Transfer transfer, Account account, TransferRequestDto requestDto) {
        if (requestDto.getTransferType().name().equals(TransferType.REFUND.name())) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.INVALID_TRANSFER_TYPE));
        }
        if (requestDto.getTransferType().name().equals(TransferType.WITHDRAW.name())) {
            if (requestDto.getAmount().compareTo(account.getBalance()) > 0) {
                throw new ServiceException(messageGenerator.getMessage(ErrorMessage.INVALID_REFUND_AMOUNT));
            }
            account.setBalance(account.getBalance().subtract(requestDto.getAmount()));
        } else {
            account.setBalance(account.getBalance().add(requestDto.getAmount()));
        }
        accountService.saveAccount(account);
        transfer.setAmount(requestDto.getAmount());
        transfer.setStatus(TransferStatus.COMPLETED);
        transferRepository.save(transfer);
    }

    private void checkCurrency(CurrencyType currency, Account account) {
        boolean existsCurrency = Arrays.stream(CurrencyType.values()).anyMatch(currency::equals);
        if (!existsCurrency || !account.getCurrencyType().name().equals(currency.name())) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.MISMATCH_CURRENCY));
        }
    }


    public TransferResponseDto refund(TransferRequestDto requestDto, BigDecimal refundAmount) {
        if (!requestDto.getTransferType().name().equals(TransferType.REFUND.name())) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.INVALID_TRANSFER_TYPE));
        }
        if (requestDto.getAmount().compareTo(refundAmount) < 0) {
            throw new ServiceException(messageGenerator.getMessage(ErrorMessage.INVALID_REFUND_AMOUNT));
        }
        TransferContext context = prepareTransferContext(requestDto);
        Customer customer = context.customer();
        Account account = context.account();

        Transfer transfer = transferMapper.toTranasfer(customer, requestDto, account);
        List<Transfer> transferList = transferRepository.findByAccountNumber(requestDto.getAmount(),
                requestDto.getAccountNumber(), TransferType.WITHDRAW, TransferStatus.COMPLETED,
                customer);
        Transfer optionalTransfer = transferList.stream().findFirst()
                .orElseThrow(() -> new ServiceException(messageGenerator
                        .getMessage(ErrorMessage.TRANSFER_NOT_FOUND)));
        account.setBalance(account.getBalance().add(refundAmount));
        transfer.setStatus(TransferStatus.COMPLETED);
        transfer.setType(TransferType.REFUND);
        transfer.setRefundedTransferId(optionalTransfer.getId());
        transfer.setAmount(refundAmount);
        transferRepository.save(transfer);
        return transferMapper.toTransferResponseDto(transfer, account, customer);

    }

    private TransferContext prepareTransferContext(TransferRequestDto requestDto) {
        Customer customer = registerCustomerService.findCustomerById(requestDto.getCustomerId());
        Account account = accountService.getAccountNumber(requestDto.getAccountNumber());
        checkAccountStatus(account);
        checkCurrency(requestDto.getCurrency(), account);
        return new TransferContext(customer, account);
    }

}