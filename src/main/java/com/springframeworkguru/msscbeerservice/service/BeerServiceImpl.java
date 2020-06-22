package com.springframeworkguru.msscbeerservice.service;

import com.springframeworkguru.msscbeerservice.api.mapper.BeerMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
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
//        return beerRepository.findById(id)
//                .map(beer -> {
//                    return beerMapper.beerToToBeerDto(beer);
//                }).orElseThrow(() -> new NotFoundException("getBeerById : " + id + " not found"));
        return BeerDTO.builder().id(id).build();
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {

//        beerDTO.setId(UUID.randomUUID());
//        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
//        Beer savedBeer = beerRepository.save(beer);
//        return beerMapper.beerToToBeerDto(savedBeer);

        return BeerDTO.builder().id(UUID.randomUUID()).build();
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {
//        beerDTO.setId(id);
//        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
//        Beer updatedBeer = beerRepository.save(beer);
//        return beerMapper.beerToToBeerDto(updatedBeer);

        return BeerDTO.builder().id(id).build();
    }

    @Override
    public String deleteBeerById(UUID id) {
        // ToDo beerRepository.deleteById(id);
        return "String";
    }

    @Override
    public Long count() {
        return beerRepository.count();
    }
}
