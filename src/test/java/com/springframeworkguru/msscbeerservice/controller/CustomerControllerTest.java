package com.springframeworkguru.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframeworkguru.msscbeerservice.api.model.CustomerDTO;
import com.springframeworkguru.msscbeerservice.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCustomerById() throws Exception {

        when(customerService.getCustomerById(any())).thenReturn(CustomerDTO.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewCustomer() throws Exception {

        // given
        CustomerDTO customerDTO = CustomerDTO.builder().name("lior lavon").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDTO);

        CustomerDTO savedCustomerDTO = CustomerDTO.builder().name("lior lavon").build();

        when(customerService.saveNewCustomer(any())).thenReturn(savedCustomerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateCustomer() throws Exception {

        CustomerDTO customerDTO = CustomerDTO.builder().name("lior lavon").build();
        String customerDtoJson = objectMapper.writeValueAsString(customerDTO);

        CustomerDTO savedCustomerDTO = CustomerDTO.builder().name("lior lavon").build();

        when(customerService.updateCustomer(any(), any())).thenReturn(savedCustomerDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isOk());

    }

    @Test
    void deleteCustomerById() throws Exception {

        when(customerService.deleteCustomerById(any())).thenReturn("SUCCESS");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/" + UUID.randomUUID().toString()))
                .andExpect(status().isOk());
    }
}