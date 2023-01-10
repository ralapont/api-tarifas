package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.service.PricesService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RequiredArgsConstructor
@RestController
public class PricesController {

    private final PricesService pricesService;

    @GetMapping("/prices")
    public ResponseEntity<PricesResponse> getRateToApply(@RequestParam( value="applicationDate") Timestamp applicationDate,
                                                         @RequestParam( value="productId") Integer productId,
                                                         @RequestParam( value="brandId") Integer brandId ){

        return ResponseEntity.ok(pricesService.getRateToApply(applicationDate, productId, brandId));
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
