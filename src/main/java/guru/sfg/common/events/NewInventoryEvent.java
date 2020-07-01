package guru.sfg.common.events;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

    public NewInventoryEvent(BeerDTO beerDto) {
        super(beerDto);
    }
}
