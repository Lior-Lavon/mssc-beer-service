package com.springframeworkguru.msscbeerservice.api.mapper;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class}) // use DateMapper to handle the date conversion
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDTO beerToBeerDto(Beer beer);
    BeerDTO beerToBeerDtoWithInventory(Beer beer);
    Beer beerDtoToBeer(BeerDTO beerDto);
}
