package com.example.apitarifas.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@JsonRootName(value = "error")
public class ErrorResponse {
    @JsonProperty(value="status")
    private HttpStatus status;
    @JsonProperty(value="mensaje")
    private String message;
    @JsonProperty(value="fechaYHoraError")
    private LocalDateTime timestamp;
    @JsonProperty(value="erroresList")
    private List<String> errors;
}
