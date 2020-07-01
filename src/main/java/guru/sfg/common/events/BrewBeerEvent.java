package guru.sfg.common.events;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

    public BrewBeerEvent(BeerDTO beerDto) {
        super(beerDto);
    }
}
