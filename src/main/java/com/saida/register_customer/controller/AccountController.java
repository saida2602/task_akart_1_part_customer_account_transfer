package com.saida.register_customer.controller;

import com.saida.register_customer.dto.request.AccountDto;
import com.saida.register_customer.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/createAccount")
    public AccountDto createAccount(@RequestBody @Valid AccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }

    @PutMapping("/updateAccount/accountId/{accountId}")
    public AccountDto updateAccount(@RequestBody AccountDto accountDto,
                                    @PathVariable Long accountId) {
        return accountService.updateAccount(accountDto, accountId);
    }

    @DeleteMapping("/deleteAccount/accountId/{accountId}")
    public void deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/getAccoounts/customerId/{customerId}")
    public List<AccountDto> getAccountsCustomerById(@PathVariable Long customerId) {
        return accountService.getAccountsCustomerById(customerId);
    }

}