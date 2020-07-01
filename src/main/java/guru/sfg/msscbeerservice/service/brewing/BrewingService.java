package guru.sfg.msscbeerservice.service.brewing;

import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.msscbeerservice.api.mapper.BeerMapper;
import guru.sfg.msscbeerservice.config.JmsConfig;
import guru.sfg.msscbeerservice.model.Beer;
import guru.sfg.msscbeerservice.repository.BeerRepository;
import guru.sfg.msscbeerservice.service.inventory.BeerInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class BrewingService {

    private final BeerRepository beerRepository;
    private final BeerInventoryService beerInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    public BrewingService(BeerRepository beerRepository, BeerInventoryService beerInventoryService, JmsTemplate jmsTemplate, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerInventoryService = beerInventoryService;
        this.jmsTemplate = jmsTemplate;
        this.beerMapper = beerMapper;
    }

    // This function will run every 5 seconds , get all beers and check the inventory
    // if inventory is smaller then the minimum on hand , then add an event to the queue BREWING_REQUEST_QUEUE
    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void checkForLowInventory(){
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            // get inventory
            Integer invQuontity = beerInventoryService.getOnhandInventory(beer.getId());
            log.debug("Min Onhand is: " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQuontity);

            if(invQuontity <= beer.getMinOnHand()){
                // create a brewing event
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
            }
        });
    }
}
