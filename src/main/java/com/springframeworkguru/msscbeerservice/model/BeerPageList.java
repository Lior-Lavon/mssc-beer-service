package com.springframeworkguru.msscbeerservice.model;

import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class BeerPageList extends PageImpl<BeerDTO> {

    public BeerPageList(List<BeerDTO> content, Pageable pageable, long total, List<Beer> list) {
        super(content, pageable, total);
    }


    public BeerPageList(List<BeerDTO> content, List<Beer> list) {
        super(content);
    }
}
