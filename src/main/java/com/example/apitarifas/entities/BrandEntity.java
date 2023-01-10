package com.example.apitarifas.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity(name="BRAND")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy="brandId")
    private List<PricesEntity> pricesList;
}
