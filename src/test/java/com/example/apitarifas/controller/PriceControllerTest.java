package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.service.PricesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({PricesController.class, PricesService.class})
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getRateToApplyTest() throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("applicationDate", "2020-06-14 10:00:00");
        requestParams.add("productId", String.valueOf(35455));
        requestParams.add("brandId", String.valueOf(1));

        MvcResult result = this.mockMvc.perform(get("/prices")
                .params(requestParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();


    }

    private PricesResponse getResponse() {
        return PricesResponse.builder()
                .productId(35455)
                .priceList(1)
                .brandId(1)
                .price(35.50)
                .startDate(Timestamp.valueOf("2011-10-02 18:48:05"))
                .endDate(Timestamp.valueOf("2011-10-02 18:48:05"))
                .build();
    }



}
