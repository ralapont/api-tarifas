package com.example.apitarifas.repository;

import com.example.apitarifas.entities.PricesEntity;
import com.example.apitarifas.utils.ConvertDates;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
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

    @DisplayName("Test query by producto, brand and dates")
    @ParameterizedTest
    @CsvSource({"2020-06-14 10:00:00, 1, 35455"})
    public void findByPriceListAndProductIdAndStartDateGreaterThanTest(ArgumentsAccessor arguments) throws Exception {

        LocalDateTime applicationDate = ConvertDates.convertStringToTimestamp(arguments.getString(0));
        Long brandId = arguments.getLong(1);
        Integer productId = arguments.getInteger(2);

        List<PricesEntity> pricesEntities = pricesRepository.findAllByBrandIdAndProductIdAndApplicationDate(brandId, productId, applicationDate);

        assertNotNull(pricesEntities);
        assertThat(pricesEntities.size()).isEqualTo(1);

        PricesEntity price = pricesEntities.get(0);

        assertThat(price.getPriceList()).isEqualTo(1);
        assertThat(price.getPrice()).isEqualTo(35.50);
        assertThat(price.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-14 00:00:00"));
        assertThat(price.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"));

    }

}
