package com.example.apitarifas.service;

import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.entities.PricesEntity;
import com.example.apitarifas.exceptions.NoRateToApplyException;
import com.example.apitarifas.repository.PricesRepository;
import com.example.apitarifas.service.impl.PricesServiceImpl;
import com.example.apitarifas.utils.ConvertDates;
import com.example.apitarifas.utils.TestDataMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PricesServiceTest {

    @Mock
    private PricesRepository pricesRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PricesServiceImpl service;

    @DisplayName("Service obtiene un price despues de discriminar")
    @Test
    public void getRateToApplyTest() throws Exception {
        when(pricesRepository.findAllByBrandIdAndProductIdAndApplicationDate(anyLong(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(TestDataMock.getPriceEntityMockList());

        when(modelMapper.map(any(PricesEntity.class), any())).thenReturn(TestDataMock.getPriceResponseMock());

        LocalDateTime applicationDate = ConvertDates.convertStringToTimestamp("2020-06-16 21:00:00");
        Integer productId = 35455;
        Long brandId = 1L;

        PricesResponse pricesResponse = service.getRateToApply(applicationDate, productId, brandId);

        assertNotNull(pricesResponse);

        assertThat(pricesResponse.getPrice()).isEqualTo(38.95);
        assertThat(pricesResponse.getPriceList()).isEqualTo(4);
        assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-15 16:00:00"));
        assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"));
    }

    @DisplayName("Service no casa con ningún precio")
    @Test
    public void getRateToApplyTest_exception() throws Exception {

        LocalDateTime applicationDate = ConvertDates.convertStringToTimestamp("2022-06-16 21:00:00");
        Integer productId = 35455;
        Long brandId = 1L;

        NoRateToApplyException thrown = Assertions.assertThrows(NoRateToApplyException.class, () -> {
            service.getRateToApply(applicationDate, productId, brandId);
        }, "No se han encontrado precios a aplicar");

        Assertions.assertEquals("No se han encontrado precios a aplicar", thrown.getMessage());


    }

}
