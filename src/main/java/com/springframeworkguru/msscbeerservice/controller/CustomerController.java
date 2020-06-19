package com.springframeworkguru.msscbeerservice.controller;

import com.springframeworkguru.msscbeerservice.api.model.CustomerDTO;
import com.springframeworkguru.msscbeerservice.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> saveNewCustomer(@Valid @RequestBody CustomerDTO customerDTO){

        CustomerDTO savedCustomer = customerService.saveNewCustomer(customerDTO);

        return new ResponseEntity<>(savedCustomer, CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDTO customerDTO){

        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomerById(@PathVariable UUID id){
        customerService.deleteCustomerById(id);
    }


}
