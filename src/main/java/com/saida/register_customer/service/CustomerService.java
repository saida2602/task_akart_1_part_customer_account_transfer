package com.saida.register_customer.service;

import com.saida.register_customer.config.MessageGenerator;
import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.dto.request.CustomerDto;
import com.saida.register_customer.error.CustomerNotFoundException;
import com.saida.register_customer.error.ErrorMessage;
import com.saida.register_customer.mapper.CustomerMapper;
import com.saida.register_customer.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper mapper;
    private final CustomerRepository registerCustomerRepository;
    private final MessageGenerator messageGenerator;

    public CustomerDto createCustomer(@Valid CustomerDto customerDto) {
        Customer customer = mapper.toCustomer(customerDto);
        registerCustomerRepository.save(customer);
        return mapper.toCustomerDto(customer);
    }

    public CustomerDto updateCustomer(CustomerDto customerDto, Long id) {
        Customer customer = findCustomerById(id);
        mapper.updateCustomer(customerDto, customer);
        registerCustomerRepository.save(customer);
        return mapper.toCustomerDto(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = findCustomerById(id);
        registerCustomerRepository.deleteById(customer.getId());
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = findCustomerById(id);
        return mapper.toCustomerDto(customer);
    }

    public Customer findCustomerById(Long id) {
        Customer customer = registerCustomerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(messageGenerator
                        .getMessage(ErrorMessage.CUSTOMER_NOT_FOUND) + id));
        return customer;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = registerCustomerRepository.findAll();
        return mapper.toCustomerDtoList(customers);
    }
}