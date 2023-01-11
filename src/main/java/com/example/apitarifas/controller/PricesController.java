package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.service.impl.PricesServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PricesController {

    private final PricesServiceImpl pricesService;

    @GetMapping("/prices")
    public ResponseEntity<PricesResponse> getRateToApply(@RequestParam( value="applicationDate")
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime applicationDate,
                                                         @RequestParam( value="productId") Integer productId,
                                                         @RequestParam( value="brandId") Long brandId ) throws Exception {

        return ResponseEntity.ok(pricesService.getRateToApply(applicationDate, productId, brandId));
    }

}
