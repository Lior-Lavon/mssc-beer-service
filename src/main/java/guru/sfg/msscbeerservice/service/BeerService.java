package guru.sfg.msscbeerservice.service;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.model.BeerPagedList;
import javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest, Boolean showInventory);

    BeerDTO getBeerById(UUID id, Boolean showInventory) throws NotFoundException;

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);

    String deleteBeerById(UUID id);

    Long count();

    BeerDTO getBeerByUpc(String upc, Boolean showInventory);

    //    ActiveMQ message
    void sendMessage(Long id);

}
