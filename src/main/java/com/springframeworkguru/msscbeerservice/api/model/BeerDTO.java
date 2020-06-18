package com.springframeworkguru.msscbeerservice.api.model;

import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {

    @Null // read only property, should not be set from outside
    private UUID id;

    @Null
    private Long version;

    @Null
    private Timestamp createdAt;

    @Null
    private Timestamp lastModifiedAt;

    @NotBlank
    private String beerName;

    @NotNull
    private BeerStyleEnum beerStyle;

    @Positive
    @NotNull
    private Long upc;

    @Positive
    @NotNull
    private BigDecimal price;

//    private Integer minOnHand;
    private Integer quantityToBrew;
}
