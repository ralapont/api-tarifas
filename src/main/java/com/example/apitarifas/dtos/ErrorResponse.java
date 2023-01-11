package com.example.apitarifas.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> errors;
}
