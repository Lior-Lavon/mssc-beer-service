package com.springframeworkguru.msscbeerservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springframeworkguru.msscbeerservice.api.model.BeerDTO;
import com.springframeworkguru.msscbeerservice.model.BeerStyleEnum;
import com.springframeworkguru.msscbeerservice.service.BeerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// disable and add the RestDock import
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//RestDoc

//RestDoc
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BeerService beerService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {

        when(beerService.getBeerById(any())).thenReturn(BeerDTO.builder().build());

        // {beerId} : example of pathParameter with RestDocs
        mockMvc.perform(get("/api/v1/beers/{beerId}", UUID.randomUUID().toString())
                .param("isCold", "yes") // isCold: example of queryParam with RestDocs
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beers-get",
                        pathParameters( // RestDoc Path Parameters
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        requestParameters( // RestDoc request parameters
                            parameterWithName("isCold").description("Is Beer cold query param")
                        ),
                        responseFields( // RestDoc Pojo parameters
                            fieldWithPath("id").description("Id  of Beer"),
                            fieldWithPath("version").description("Version number"),
                            fieldWithPath("createdAt").description("Date created"),
                            fieldWithPath("lastModifiedAt").description("Last Modified date"),
                            fieldWithPath("beerName").description("Beer Name"),
                            fieldWithPath("beerStyle").description("Beer Style"),
                            fieldWithPath("upc").description("UPC of Beer"),
                            fieldWithPath("price").description("Price"),
                            fieldWithPath("quantityOnHand").description("Quantity On Hand")
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {

        // given
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityOnHand(200)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        when(beerService.saveNewBeer(any())).thenReturn(BeerDTO.builder().build());

        ConstrainedFields fields = new ConstrainedFields(BeerDTO.class);

        mockMvc.perform(post("/api/v1/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beers-post",
                        requestFields(
                                fields.withPath("id").ignored(),
                                fields.withPath("version").ignored(),
                                fields.withPath("createdAt").ignored(),
                                fields.withPath("lastModifiedAt").ignored(),
                                fields.withPath("beerName").description("Name of Beer"),
                                fields.withPath("beerStyle").description("Style of Beer"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price of Beer"),
                                fields.withPath("quantityOnHand").ignored()

                        )));
    }

    @Test
    void updateBeerById() throws Exception {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityOnHand(200)
                .upc(33701234870L)
                .price(new BigDecimal("12.95"))
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDTO);

        when(beerService.saveNewBeer(any())).thenReturn(BeerDTO.builder().build());

        mockMvc.perform(put("/api/v1/beers/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerById() throws Exception {

        when(beerService.deleteBeerById(any())).thenReturn("SUCCESS");

        mockMvc.perform(delete("/api/v1/beers/" + UUID.randomUUID().toString()))
                .andExpect(status().isOk());

    }


//    This calls is used to add Constraint descriptors
    private static class ConstrainedFields{
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input){
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path).attributes(key("constraints").value(
                    StringUtils.collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }

}