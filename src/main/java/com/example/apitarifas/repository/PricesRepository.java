package com.example.apitarifas.repository;

import com.example.apitarifas.entities.PricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PricesRepository extends JpaRepository<PricesEntity, Long> {
    List<PricesEntity> findAllByBrandIdAndProductIdAndApplicationDate(Long brandId, Integer productId, LocalDateTime applicationDate);
}
