package com.springframeworkguru.msscbeerservice.api.mapper;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeerMapper {

    BeerMapper INSTANCE = Mappers.getMapper(BeerMapper.class);

    BeerDTO beerToToBeerDto(Beer beer);
    Beer beerDtoToBeer(BeerDTO beerDto);
}
