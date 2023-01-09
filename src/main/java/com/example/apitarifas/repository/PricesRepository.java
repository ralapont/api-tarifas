package com.example.apitarifas.repository;

import com.example.apitarifas.entities.PricesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricesRepository extends JpaRepository<PricesEntity, Long> {
}
