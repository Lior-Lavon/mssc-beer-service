package com.springframeworkguru.msscbeerservice.service.inventory;

import com.springframeworkguru.msscbeerservice.bootstrap.LoadData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@Disabled // used for manual testing
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

    @Autowired
    BeerInventoryService beerInventoryService;

    @MockBean
    RestTemplate restTemplate;

    @Test
    void getOnhandInventory() {

        Integer qoh = beerInventoryService.getOnhandInventory(LoadData.Beer_1_UUID);

        System.out.println("getOnhandInventory : " + qoh);
    }

}