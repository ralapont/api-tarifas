package com.example.apitarifas.exceptions;

public class NoRateToApplyException extends RuntimeException {

    public NoRateToApplyException(String message) {
        super(message);
    }
}
