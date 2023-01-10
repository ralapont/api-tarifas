package com.example.apitarifas.service;

import com.example.apitarifas.dtos.PricesResponse;

import java.time.LocalDateTime;

public interface PricesService {

    PricesResponse getRateToApply(LocalDateTime applicationDate, Integer productId, Long brandId );
}
