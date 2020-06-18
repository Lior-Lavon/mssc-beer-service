package com.springframeworkguru.msscbeerservice.service;
import com.springframeworkguru.msscbeerservice.api.model.CustomerDTO;

import java.util.UUID;

public interface CustomerService {

    CustomerDTO getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO);

    void deleteCustomerById(UUID id);

}
