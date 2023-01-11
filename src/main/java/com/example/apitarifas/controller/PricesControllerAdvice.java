package com.example.apitarifas.controller;

import com.example.apitarifas.dtos.ErrorResponse;
import com.example.apitarifas.exceptions.NoRateToApplyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class PricesControllerAdvice {

    @ExceptionHandler(value = NoRateToApplyException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(NoRateToApplyException ex, WebRequest request) {
        ErrorResponse message = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NO_CONTENT)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<ErrorResponse>(message, HttpStatus.NOT_FOUND);
    }
}
