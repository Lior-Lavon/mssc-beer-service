package guru.sfg.msscbeerservice.controller;

import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.model.BeerPagedList;
import guru.sfg.msscbeerservice.service.BeerService;
import javassist.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 10;

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping(produces = { "application/json" }, path = "/beers")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) String beerStyle,
                                                   @RequestParam(name = "showInventory", defaultValue = "false", required = false) Boolean showInventory){

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

    @GetMapping("/beers/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable UUID beerId,
                                               @RequestParam(name = "showInventory", defaultValue = "false", required = false) Boolean showInventory) throws NotFoundException {

        if(showInventory==null)
            showInventory = false;

        System.out.println("getBeerById: I was called");
        BeerDTO dto = beerService.getBeerById(beerId, showInventory);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/beers")
    public ResponseEntity<BeerDTO> saveNewBeer(@RequestBody @Valid BeerDTO beerDto){
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), CREATED);
    }

    @PutMapping("/beers/{id}")
    public ResponseEntity<BeerDTO> updateBeerById(@PathVariable UUID id, @RequestBody @Valid BeerDTO beerDTO){
        return new ResponseEntity<>(beerService.updateBeer(id, beerDTO), HttpStatus.OK);
    }

    @DeleteMapping("/beers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeerById(@PathVariable UUID id){
        beerService.deleteBeerById(id);
    }

    @GetMapping("/beerUpc/{upc}")
    public ResponseEntity<BeerDTO> getByUpc(@PathVariable String upc,
                @RequestParam(name = "showInventory", defaultValue = "false", required = false) Boolean showInventory){

        beerService.sendMessage(5L);

        return new ResponseEntity<>(beerService.getBeerByUpc(upc, showInventory), HttpStatus.OK);
    }

}
