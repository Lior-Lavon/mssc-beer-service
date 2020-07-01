package guru.sfg.msscbeerservice.api.mapper;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.model.Beer;
import guru.sfg.msscbeerservice.service.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService){
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDto(Beer beer){
        BeerDTO dto = mapper.beerToBeerDto(beer);
        dto.setQuantityOnHand(null);
        return dto;
    }

    @Override
    public BeerDTO beerToBeerDtoWithInventory(Beer beer){
        BeerDTO dto = mapper.beerToBeerDto(beer);

        // make an API call to the inventory service to get the available inventory amount
        dto.setQuantityOnHand(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDTO beerDTO){
        return mapper.beerDtoToBeer(beerDTO);
    }
}
