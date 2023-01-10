package com.example.apitarifas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PricesResponse {

    private Integer productId;
    private Integer brandId;
    private Integer priceList;
    private Timestamp startDate;
    private Timestamp endDate;
    private Double price;
}
