package com.springframeworkguru.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(BeerController.class)

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class BeerControllerTest {

//    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    BeerController beerController;

    @Mock
    BeerService beerService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(beerController).build();
    }

    @Test
    void getBeerById() throws Exception {

        when(beerService.getBeerById(any())).thenReturn(BeerDTO.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beers/" + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewBeer() throws Exception {

        // given
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();

        when(beerService.saveNewBeer(any())).thenReturn(savedBeerDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();

        when(beerService.updateBeer(any(), any())).thenReturn(savedBeerDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/beers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerById() throws Exception {

        when(beerService.deleteBeerById(any())).thenReturn("SUCCESS");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/beers/" + UUID.randomUUID().toString()))
                .andExpect(status().isOk());

    }
}