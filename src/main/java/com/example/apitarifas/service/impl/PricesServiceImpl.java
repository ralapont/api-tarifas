package com.example.apitarifas.service.impl;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.entities.PricesEntity;
import com.example.apitarifas.repository.PricesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PricesServiceImpl {

    private final PricesRepository pricesRepository;

    public PricesResponse getRateToApply(LocalDateTime applicationDate,
                                         Integer productId,
                                         Long brandId ) {
        log.info("applicationDate: {} productId {} brandId {} ", applicationDate, productId, brandId);
        List<PricesEntity> prices = pricesRepository.findAllByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);


        return null;
    }

}
