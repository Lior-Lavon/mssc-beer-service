package com.springframeworkguru.msscbeerservice.bootstrap;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Component
public class LoadData implements CommandLineRunner {

    public static final UUID Beer_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID Beer_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID Beer_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    public static final String Beer_1_UPC = "00732834762897";
    public static final String Beer_2_UPC = "00732834762898";
    public static final String Beer_3_UPC = "00732834762899";

    private final BeerService beerService;

    public LoadData(BeerService beerService) {
        this.beerService = beerService;
    }

    @Override
    public void run(String... args) throws Exception {

        loadBeer();
    }

    private void loadBeer() {

        if(beerService.count()==0){

            BeerDTO save1 = beerService.saveNewBeer(BeerDTO.builder()
                    .id(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"))
                    .beerName("Mango Bobs")
                    .beerStyle("IPA")
                    .quantityToBrew(50)
                    .upc(Beer_1_UPC)
                    .price(new BigDecimal("12.95"))
                    .minOnHand(12)
                    .build());

            BeerDTO save2 = beerService.saveNewBeer(BeerDTO.builder()
                    .id(UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd"))
                    .beerName("Galaxy Cat")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(100)
                    .upc(Beer_2_UPC)
                    .price(new BigDecimal("11.95"))
                    .minOnHand(12)
                    .build());

            BeerDTO save3 = beerService.saveNewBeer(BeerDTO.builder()
                    .id(UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08"))
                    .beerName("No Hammers On The Bar")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(150)
                    .upc(Beer_3_UPC)
                    .price(new BigDecimal("11.95"))
                    .minOnHand(12)
                    .build());
        }
    }
}
