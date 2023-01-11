package com.example.apitarifas.utils;

import com.example.apitarifas.dtos.ErrorResponse;
import com.example.apitarifas.dtos.PricesResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectsDeserializer {

    public static PricesResponse deserializerJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.readValue(json, PricesResponse.class);
    }

    public static ErrorResponse deserializerErrorJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.readValue(json, ErrorResponse.class);
    }

}
