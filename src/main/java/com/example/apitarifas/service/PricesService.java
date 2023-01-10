package com.example.apitarifas.service;

import com.example.apitarifas.dtos.PricesResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
public class PricesService {

    public PricesResponse getRateToApply(Timestamp applicationDate,
                                         Integer productId,
                                         Integer brandId ) {
        log.info("applicationDate: {} productId {} brandId {} ", applicationDate, productId, brandId);
        return getResponse();
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
