package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.mapper.BeerMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.controller.NotFoundException;
import com.springframeworkguru.msscbeerservice.model.Beer;
import com.springframeworkguru.msscbeerservice.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO getBeerById(UUID id) throws NotFoundException {
        return beerRepository.findById(id)
                .map(beer -> {
                    return beerMapper.beerToToBeerDto(beer);
                }).orElseThrow(() -> new NotFoundException("getBeerById : " + id + " not found"));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.beerToToBeerDto(savedBeer);
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {

        Beer beer = beerRepository.findById(id).orElseThrow(() -> new NotFoundException("getBeerById : " + id + " not found"));
        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.beerToToBeerDto(beerRepository.save(beer));
    }

    @Override
    public String deleteBeerById(UUID id) {
        beerRepository.deleteById(id);
        return "String";
    }

    @Override
    public Long count() {
        return beerRepository.count();
    }
}
