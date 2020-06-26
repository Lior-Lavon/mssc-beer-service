package com.springframeworkguru.msscbeerservice.controller;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.BeerPagedList;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) String beerStyle,
                                                   @RequestParam(value = "showInventory", defaultValue = "false", required = false) Boolean showInventory){

        if(showInventory==null)
            showInventory = false;

        if(pageNumber==null || pageNumber<0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize==null || pageSize<0){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventory);
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id,
                                               @RequestParam(name = "showInventoryParam", defaultValue = "false", required = false) Boolean showInventory) throws NotFoundException {
        return new ResponseEntity<>(beerService.getBeerById(id, showInventory), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BeerDTO> saveNewBeer(@RequestBody @Valid BeerDTO beerDto){
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeerDTO> updateBeerById(@PathVariable UUID id, @RequestBody @Valid BeerDTO beerDTO){
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeerById(@PathVariable UUID id){
        beerService.deleteBeerById(id);
    }


}
