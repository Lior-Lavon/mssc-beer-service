package com.springframeworkguru.msscbeerservice.api.mapper;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import com.springframeworkguru.msscbeerservice.service.inventory.BeerInventoryService;
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
        dto.setQuantityToBrew(null);
        return dto;
    }

    @Override
    public BeerDTO beerToBeerDtoWithInventory(Beer beer){
        BeerDTO dto = mapper.beerToBeerDto(beer);

        // make an API call to the inventory service to get the available inventory amount
        dto.setQuantityToBrew(beerInventoryService.getOnhandInventory(beer.getId()));
        return dto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDTO beerDTO){
        return mapper.beerDtoToBeer(beerDTO);
    }
}
