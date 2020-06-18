package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import javassist.NotFoundException;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById(UUID id) throws NotFoundException;

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);

    void deleteBeerById(UUID id);

    Long count();
}
