package com.springframeworkguru.msscbeerservice.bootstrap;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import com.springframeworkguru.msscbeerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class LoadData implements CommandLineRunner {

    private final BeerService beerService;
    private final CustomerService customerService;

    public LoadData(BeerService beerService, CustomerService customerService) {
        this.beerService = beerService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {

        loadBeer();
    }

    private void loadBeer() {

        if(beerService.count()==0){

            BeerDTO save1 = beerService.saveNewBeer(BeerDTO.builder()
                    .beerName("Mango Bobs")
                    .beerStyle(BeerStyleEnum.IPA)
                    .quantityOnHand(200)
                    .upc(33701234870L)
                    .price(new BigDecimal("12.95"))
                    .build());

            BeerDTO save2 = beerService.saveNewBeer(BeerDTO.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .quantityOnHand(200)
                    .upc(33701234871L)
                    .price(new BigDecimal("11.95"))
                    .build());
        }
    }
}
