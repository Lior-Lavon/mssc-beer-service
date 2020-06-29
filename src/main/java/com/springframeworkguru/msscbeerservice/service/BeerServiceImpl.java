package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.mapper.BeerMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.controller.NotFoundException;
import com.springframeworkguru.msscbeerservice.model.Beer;
import com.springframeworkguru.msscbeerservice.model.BeerPagedList;
import com.springframeworkguru.msscbeerservice.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventory == false")
    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest, Boolean showInventory) {

        System.out.println("Service listBeers: I was called");


        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            // search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            // search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            // search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventory){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventory == false")
    @Override
    public BeerDTO getBeerById(UUID beerId, Boolean showInventory) throws NotFoundException {

        if(showInventory){
            return beerRepository.findById(beerId)
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .orElseThrow(() -> new NotFoundException("getBeerById : " + beerId + " not found"));
        } else {
            return beerRepository.findById(beerId)
                    .map(beerMapper::beerToBeerDto)
                    .orElseThrow(() -> new NotFoundException("getBeerById : " + beerId + " not found"));
        }
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {

        Beer beer = beerRepository.findById(id).orElseThrow(() -> new NotFoundException("getBeerById : " + id + " not found"));
        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
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

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventory == false")
    @Override
    public BeerDTO getBeerByUpc(String upc, Boolean showInventory) {

        System.out.println("Service getBeerByUpc: I was called");

        BeerDTO beerDTO = null;
        Beer beer = beerRepository.findByUpc(upc);

        if(showInventory){
            beerDTO = beerMapper.beerToBeerDtoWithInventory(beer);
        } else {
            beerDTO = beerMapper.beerToBeerDto(beer);
        }

        return beerDTO;
    }
}
