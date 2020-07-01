package guru.sfg.msscbeerservice.service.brewing;

import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.common.events.NewInventoryEvent;
import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.config.JmsConfig;
import guru.sfg.msscbeerservice.model.Beer;
import guru.sfg.msscbeerservice.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrewBeerListener {

    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    // Listen on the queue for events
    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
    public void listen(BrewBeerEvent event){

        BeerDTO beerDTO = event.getBeerDto();

        // get the beer from the DB
        Optional<Beer> optionalBeer = beerRepository.findById(beerDTO.getId());
        if(optionalBeer.isPresent()){
            Beer beer = optionalBeer.get();

            // this is emulating a brewing process
            // take the amount we need and set it to the amount we have in our hand
            beerDTO.setQuantityOnHand(beer.getQuantityToBrew());

            NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDTO);
            log.debug("Brewed beer " + beer.getMinOnHand() + ";  QOH: " + beerDTO.getQuantityOnHand());

            jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
        }
    }
}
