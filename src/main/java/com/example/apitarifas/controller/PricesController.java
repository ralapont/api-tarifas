package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.service.PricesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@AllArgsConstructor
@RestController
public class PricesController {

    private PricesService pricesService;

    @GetMapping("/prices")
    public ResponseEntity<PricesResponse> getRateToApply(@RequestParam Timestamp applicationDate,
                                                         @RequestParam Integer productId,
                                                         @RequestParam Integer brandId ){

        return ResponseEntity.ok(pricesService.getRateToApply(applicationDate, productId, brandId));
    }

}
