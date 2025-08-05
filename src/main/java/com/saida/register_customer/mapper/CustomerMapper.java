package com.saida.register_customer.mapper;

import com.saida.register_customer.domain.Customer;
import com.saida.register_customer.dto.request.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerDto customerDto);

    CustomerDto toCustomerDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    void updateCustomer(CustomerDto dto, @MappingTarget Customer customer);

    List<CustomerDto> toCustomerDtoList(List<Customer> customers);
}
