package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.ErrorResponse;
import com.example.apitarifas.dtos.PricesResponse;
import com.example.apitarifas.repository.PricesRepository;
import com.example.apitarifas.service.impl.PricesServiceImpl;
import com.example.apitarifas.utils.ConvertDates;
import com.example.apitarifas.utils.ObjectsDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Test Integration with cases required")
    @ParameterizedTest
    @CsvSource({
            "2020-06-14 10:00:00, 1, 35455",
            "2020-06-14 16:00:00, 1, 35455",
            "2020-06-14 21:00:00, 1, 35455",
            "2020-06-15 10:00:00, 1, 35455",
            "2020-06-16 21:00:00, 1, 35455"
    })
    public void getRateToApplyTest(ArgumentsAccessor arguments) throws Exception {

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("applicationDate", arguments.getString(0));
        requestParams.add("brandId", arguments.getString(1));
        requestParams.add("productId", arguments.getString(2));

        MvcResult result = this.mockMvc.perform(get("/prices")
                .params(requestParams))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        PricesResponse pricesResponse = ObjectsDeserializer.deserializerJson(content);

        assertNotNull(pricesResponse);

        if (arguments.getString(0).equals("2020-06-14 10:00:00")) {
            assertThat(pricesResponse.getPrice()).isEqualTo(35.5);
            assertThat(pricesResponse.getPriceList()).isEqualTo(1);
            assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-14 00:00:00"));
            assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"));
        }

        if (arguments.getString(0).equals("2020-06-14 16:00:00")) {
            assertThat(pricesResponse.getPrice()).isEqualTo(25.45);
            assertThat(pricesResponse.getPriceList()).isEqualTo(2);
            assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-14 15:00:00"));
            assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-14 18:30:00"));
        }

        if (arguments.getString(0).equals("2020-06-14 21:00:00")) {
            assertThat(pricesResponse.getPrice()).isEqualTo(35.5);
            assertThat(pricesResponse.getPriceList()).isEqualTo(1);
            assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-14 00:00:00"));
            assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"));
        }

        if (arguments.getString(0).equals("2020-06-15 10:00:00")) {
            assertThat(pricesResponse.getPrice()).isEqualTo(30.5);
            assertThat(pricesResponse.getPriceList()).isEqualTo(3);
            assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-15 00:00:00"));
            assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-15 11:00:00"));
        }

        if (arguments.getString(0).equals("2020-06-16 21:00:00")) {
            assertThat(pricesResponse.getPrice()).isEqualTo(38.95);
            assertThat(pricesResponse.getPriceList()).isEqualTo(4);
            assertThat(pricesResponse.getStartDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-06-15 16:00:00"));
            assertThat(pricesResponse.getEndDate()).isEqualTo(ConvertDates.convertStringToTimestamp("2020-12-31 23:59:59"));
        }

    }

    @DisplayName("Test Integration with cases required")
    @ParameterizedTest
    @CsvSource({
            "2022-06-14 10:00:00, 1, 35455"
    })
    public void getRateToApplyTest_noRateToApply(ArgumentsAccessor arguments) throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("applicationDate", arguments.getString(0));
        requestParams.add("brandId", arguments.getString(1));
        requestParams.add("productId", arguments.getString(2));

        MvcResult result = this.mockMvc.perform(get("/prices")
                        .params(requestParams))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ErrorResponse errorResponse = ObjectsDeserializer.deserializerErrorJson(content);

        assertNotNull(errorResponse);

        if (arguments.getString(0).equals("2022-06-14 10:00:00")) {
            assertThat(errorResponse.getMessage()).isEqualTo("No se han encontrado precios a aplicar");
            assertThat(errorResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT);
        }
    }

}
