package com.example.apitarifas.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDates {
    public static LocalDateTime convertStringToTimestamp(String str) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(str, formatter);

    }
}
