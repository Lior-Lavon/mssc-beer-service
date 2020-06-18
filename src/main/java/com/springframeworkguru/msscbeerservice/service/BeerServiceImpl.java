package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.mapper.BeerMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import com.springframeworkguru.msscbeerservice.repository.BeerRepository;
import javassist.NotFoundException;
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
        beerDTO.setId(id);
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        Beer updatedBeer = beerRepository.save(beer);
        return beerMapper.beerToToBeerDto(updatedBeer);
    }

    @Override
    public void deleteBeerById(UUID id) {
        beerRepository.deleteById(id);
    }

    @Override
    public Long count() {
        return beerRepository.count();
    }
}
