package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;

import java.util.UUID;

public interface BeerService {

    BeerDTO getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeer(UUID id, BeerDTO beerDTO);

    void deleteBeerById(UUID id);
}
