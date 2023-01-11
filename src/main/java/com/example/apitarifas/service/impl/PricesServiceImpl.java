package com.example.apitarifas.service.impl;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.entities.PricesEntity;
import com.example.apitarifas.exceptions.NoRateToApplyException;
import com.example.apitarifas.repository.PricesRepository;
import com.example.apitarifas.service.PricesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class PricesServiceImpl implements PricesService {

    private final PricesRepository pricesRepository;
    private final ModelMapper modelMapper;

    public PricesResponse getRateToApply(LocalDateTime applicationDate,
                                         Integer productId,
                                         Long brandId ) throws NoRateToApplyException {
        log.info("applicationDate: {} productId {} brandId {} ", applicationDate, productId, brandId);
        List<PricesEntity> prices = pricesRepository.findAllByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        return prices.stream()
                .sorted(Comparator.comparingInt(PricesEntity::getPriority).reversed())
                .findAny()
                .map(x -> modelMapper.map(x, PricesResponse.class))
                .orElseThrow(() -> new NoRateToApplyException("No se han encontrado precios a aplicar"));
    }

}
