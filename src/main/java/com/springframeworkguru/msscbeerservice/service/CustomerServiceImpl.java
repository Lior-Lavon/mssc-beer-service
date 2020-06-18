package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {


    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return CustomerDTO.builder().name("lior").build();
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        customerDTO.setId(UUID.randomUUID());
        return customerDTO;
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        return customerDTO;
    }

    @Override
    public void deleteCustomerById(UUID id) {
        // delete Customer
    }
}
