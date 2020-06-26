package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.BeerPagedList;
import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
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
}
