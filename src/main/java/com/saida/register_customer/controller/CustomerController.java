package com.saida.register_customer.controller;

import com.saida.register_customer.dto.request.CustomerDto;
import com.saida.register_customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService registerCustomerService;

    @PostMapping("/createCustomer")
    public CustomerDto createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        return registerCustomerService.createCustomer(customerDto);
    }

    @PutMapping("/updateCustomer/customerId/{id}")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto, @PathVariable Long id) {
        return registerCustomerService.updateCustomer(customerDto, id);
    }

    @DeleteMapping("/deleteCustomer/customerId/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        registerCustomerService.deleteCustomer(id);
    }

    @GetMapping("/getCustomer/customerId/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        return registerCustomerService.getCustomerById(id);
    }

    @GetMapping("/getAllCustomers")
    public List<CustomerDto> getAllCustomers() {
        return registerCustomerService.getAllCustomers();
    }

}
