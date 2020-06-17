package com.springframeworkguru.msscbeerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    private UUID id;

    private String beerName;

    private BeerStyleEnum beerStyle;

    private Long upc;

    private Integer quantityOnHand;

    private BigDecimal price;

    private Integer version;

    private OffsetDateTime createdDate;

    private OffsetDateTime lastModifiedDate;
}
