package guru.sfg.msscbeerservice.api.mapper;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.model.Beer;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class}) // use DateMapper to handle the date conversion
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

    BeerDTO beerToBeerDto(Beer beer);
    BeerDTO beerToBeerDtoWithInventory(Beer beer);
    Beer beerDtoToBeer(BeerDTO beerDto);
}
