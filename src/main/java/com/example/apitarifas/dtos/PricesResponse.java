package com.example.apitarifas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonRootName(value = "precio")
public class PricesResponse {

    @JsonProperty(value="producto")
    private Integer productId;
    @JsonProperty(value="rama")
    private Integer brandId;
    @JsonProperty(value="listaPrecios")
    private Integer priceList;
    @JsonProperty(value="fechaComienzo")
    private LocalDateTime startDate;
    @JsonProperty(value="fechaFin")
    private LocalDateTime endDate;
    @JsonProperty(value="precio")
    private Double price;
}
