package guru.sfg.common.events;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 4169038197605694632L;

    private BeerDTO beerDto;
}
