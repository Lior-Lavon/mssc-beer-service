package com.springframeworkguru.msscbeerservice.controller;

import com.springframeworkguru.msscbeerservice.api.mapper.BeerMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.Beer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerMapper beerMapper;

    public BeerController(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id){
//
//        return new ResponseEntity<BeerDTO>(BeerDTO.builder().build(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BeerDTO getBeerById(@PathVariable UUID id){

        return BeerDTO.builder().build();
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BeerDTO saveNewBeer(@RequestBody BeerDTO beerDto){

        return beerDto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BeerDTO updateBeerById(@PathVariable UUID id, @RequestBody BeerDTO beerDTO){

        beerDTO.setId(id);
        return beerDTO;
    }
}
