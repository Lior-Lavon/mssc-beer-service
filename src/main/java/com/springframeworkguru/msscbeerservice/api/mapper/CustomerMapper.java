package com.springframeworkguru.msscbeerservice.api.mapper;

import com.springframeworkguru.msscbeerservice.api.model.CustomerDTO;
import com.springframeworkguru.msscbeerservice.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerDTO customerToCustomerDTO(Customer customer);
    Customer customerDTOToCustomer(CustomerDTO customerDTO);

}
