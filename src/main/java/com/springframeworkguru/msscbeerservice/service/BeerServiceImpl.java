package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDTO getBeerById(UUID id) {
        return BeerDTO.builder().beerName("Galaxy Cat").beerStyle(BeerStyleEnum.PALE_ALE).build();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        beerDTO.setId(UUID.randomUUID());
        return beerDTO;
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
        beerDTO.setId(id);
        return beerDTO;
    }

    @Override
    public void deleteBeerById(UUID id) {

        log.debug("Deleting a beer ...");
    }
}
