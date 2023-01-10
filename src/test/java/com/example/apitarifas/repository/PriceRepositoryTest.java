package com.example.apitarifas.repository;

import com.example.apitarifas.entities.PricesEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PriceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PricesRepository pricesRepository;

    @Test
    public void findByPriceListAndProductIdAndStartDateGreaterThan_20200614100000_Test() throws Exception {

        LocalDateTime applicationDate = convertStringToTimestamp("2020-06-14 10:00:00");
        Long brandId = 1L;
        Integer productId = 35455;

        List<PricesEntity> pricesEntities = pricesRepository.findAllByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        assertNotNull(pricesEntities);
        assertThat(pricesEntities.size()).isEqualTo(1);

    }

    private LocalDateTime convertStringToTimestamp(String str) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);

    }

}
