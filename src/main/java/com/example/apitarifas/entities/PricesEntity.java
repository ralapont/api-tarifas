package com.example.apitarifas.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@NamedQuery(name = "PricesEntity.findAllByBrandIdAndProductIdAndApplicationDate",
        query = "select p from PRICES p where p.brandId.id = ?1 and p.productId = ?2 and p.startDate <= ?3 and p.endDate > ?3")
@Entity(name="PRICES")
public class PricesEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="BRAND_ID", nullable=false)
    private BrandEntity brandId;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Integer priceList;

    @Column(name = "PRODUCT_ID")
    private Integer productId;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CURR")
    private String currency;


}
