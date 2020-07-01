package guru.sfg.msscbeerservice.service;

import guru.sfg.msscbeerservice.api.mapper.BeerMapper;
import guru.sfg.msscbeerservice.api.model.BeerDTO;
import guru.sfg.msscbeerservice.config.JmsConfig;
import guru.sfg.msscbeerservice.controller.NotFoundException;
import guru.sfg.msscbeerservice.model.Beer;
import guru.sfg.msscbeerservice.model.BeerPagedList;
import guru.sfg.msscbeerservice.repository.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    private final JmsTemplate jmsTemplate;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper, JmsTemplate jmsTemplate) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventory == false")
    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest, Boolean showInventory) {

        System.out.println("Service listBeers: I was called");


        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            // search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            // search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            // search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventory){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        } else {
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }

        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#showInventory == false")
    @Override
    public BeerDTO getBeerById(UUID beerId, Boolean showInventory) throws NotFoundException {

        if(showInventory){
            return beerRepository.findById(beerId)
                    .map(beerMapper::beerToBeerDtoWithInventory)
                    .orElseThrow(() -> new NotFoundException("getBeerById : " + beerId + " not found"));
        } else {
            return beerRepository.findById(beerId)
                    .map(beerMapper::beerToBeerDto)
                    .orElseThrow(() -> new NotFoundException("getBeerById : " + beerId + " not found"));
        }
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.beerDtoToBeer(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public BeerDTO updateBeer(UUID id, BeerDTO beerDTO) {

        Beer beer = beerRepository.findById(id).orElseThrow(() -> new NotFoundException("getBeerById : " + id + " not found"));
        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }

    @Override
    public String deleteBeerById(UUID id) {
        beerRepository.deleteById(id);
        return "String";
    }

    @Override
    public Long count() {
        return beerRepository.count();
    }

    @Cacheable(cacheNames = "beerUpcCache", key = "#upc", condition = "#showInventory == false")
    @Override
    public BeerDTO getBeerByUpc(String upc, Boolean showInventory) {

        System.out.println("Service getBeerByUpc: I was called");

        BeerDTO beerDTO = null;
        Beer beer = beerRepository.findByUpc(upc);

        if(showInventory){
            beerDTO = beerMapper.beerToBeerDtoWithInventory(beer);
        } else {
            beerDTO = beerMapper.beerToBeerDto(beer);
        }

        return beerDTO;
    }

    @Override
    public void sendMessage(Long id) {

        Map<String, String> actionMap = new HashMap<>();
        actionMap.put("id", String.valueOf(id));

        log.info("Sending the index request through queue message");
        jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, actionMap);

    }
}
