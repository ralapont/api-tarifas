package com.example.apitarifas.utils;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.entities.BrandEntity;
import com.example.apitarifas.entities.PricesEntity;

import java.util.Arrays;
import java.util.List;

public class TestDataMock {

    public static PricesEntity getPriceEntity1Mock() throws Exception {

        return PricesEntity.builder()
                .id(1L)
                .brandId(getBrandEntityMock())
                .productId(35455)
                .priceList(4)
                .startDate(ConvertDates.convertStringToTimestamp("2020-06-15 16:00:00"))
                .endDate(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"))
                .priority(1)
                .price(38.95)
                .currency("EUR")
                .build();
    }

    public static PricesEntity getPriceEntity2Mock() throws Exception {

        return PricesEntity.builder()
                .id(1L)
                .brandId(getBrandEntityMock())
                .productId(35455)
                .priceList(1)
                .startDate(ConvertDates.convertStringToTimestamp("2020-06-14 00:00:00"))
                .endDate(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"))
                .priority(0)
                .price(35.50)
                .currency("EUR")
                .build();
    }

    public static List<PricesEntity> getPriceEntityMockList() throws Exception {
        return Arrays.asList(getPriceEntity1Mock(), getPriceEntity2Mock());
    }

    public static PricesResponse getPriceResponseMock() throws Exception {
        return PricesResponse.builder()
                .productId(35455)
                .brandId(1)
                .priceList(4)
                .startDate(ConvertDates.convertStringToTimestamp("2020-06-15 16:00:00"))
                .endDate(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"))
                .price(38.95)
                .build();
    }

    public static BrandEntity getBrandEntityMock() {

        return BrandEntity.builder()
                .id(1L)
                .description("ZARA")
                .build();
    }
}
