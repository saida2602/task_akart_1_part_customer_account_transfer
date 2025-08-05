package com.saida.register_customer.service;

import com.saida.register_customer.config.MessageGenerator;
import com.saida.register_customer.domain.Account;
import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.dto.request.AccountDto;
import com.saida.register_customer.error.ServiceException;
import com.saida.register_customer.mapper.AccountMapper;
import com.saida.register_customer.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saida.register_customer.error.ErrorMessage.ACCOUNT_NOT_FOUND;
import static com.saida.register_customer.error.ErrorMessage.DUPLICATE_ACCOUNT_NUMBER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final CustomerService customerService;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final MessageGenerator messageGenerator;

    public AccountDto createAccount(AccountDto accountDto) {
        String accountNumber = accountDto.getAccountNumber();
        if (accountRepository.existsAccountByAccountNumber(accountNumber)) {
            throw new ServiceException(messageGenerator.getMessage(DUPLICATE_ACCOUNT_NUMBER) + accountNumber);
        }
        Customer customer = customerService.findCustomerById(accountDto.getCustomerId());
        Account account = accountMapper.toAccount(accountDto, customer);
        accountRepository.save(account);
        return accountMapper.toAccountDto(account);
    }


    public AccountDto updateAccount(AccountDto accountDto, Long accountId) {
        Account account = getAccountById(accountId);
        String accountNumber = accountDto.getAccountNumber();
        Account existAccount = getAccountNumber(accountNumber);
        if (accountRepository.existsAccountByAccountNumber(accountNumber)
                && !accountId.equals(existAccount.getId())) {
            throw new ServiceException(messageGenerator.getMessage(DUPLICATE_ACCOUNT_NUMBER) + accountNumber);
        }
        Customer customer = customerService.findCustomerById(accountDto.getCustomerId());
        accountMapper.updateAccountWithCustomer(accountDto, account, customer);
        saveAccount(account);
        return accountMapper.toAccountDto(account);

    }

    public void deleteAccount(Long accountId) {
        Account account = getAccountById(accountId);
        accountRepository.deleteById(account.getId());
    }

    public List<AccountDto> getAccountsCustomerById(Long customerId) {
        Customer customer = customerService.findCustomerById(customerId);
        List<Account> accounts = accountRepository.findAccountsByCustomerId(customer.getId());
        return accountMapper.toAccountsDto(accounts);
    }


    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ServiceException(messageGenerator
                .getMessage(ACCOUNT_NOT_FOUND) + accountId));
    }

    public Account getAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new ServiceException(messageGenerator
                .getMessage(ACCOUNT_NOT_FOUND) + accountNumber));
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
