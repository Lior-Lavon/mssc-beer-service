package com.springframeworkguru.msscbeerservice.controller;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/beers")
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID id) throws NotFoundException {
        BeerDTO beerDTO = beerService.getBeerById(id);
        return new ResponseEntity<>(beerDTO, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BeerDTO> saveNewBeer(@RequestBody @Valid BeerDTO beerDto){

        BeerDTO savedBeerDTO = beerService.saveNewBeer(beerDto);

        return new ResponseEntity<>(savedBeerDTO, CREATED);

//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(savedBeerDTO.getId())
//                .toUri();
//        return ResponseEntity.status(CREATED).header(HttpHeaders.LOCATION, String.valueOf(location)).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BeerDTO> updateBeerById(@PathVariable UUID id, @RequestBody @Valid BeerDTO beerDTO){

        BeerDTO updatedBeer = beerService.updateBeer(id, beerDTO);

        return new ResponseEntity<>(updatedBeer, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeerById(@PathVariable UUID id){
        beerService.deleteBeerById(id);
    }


}
