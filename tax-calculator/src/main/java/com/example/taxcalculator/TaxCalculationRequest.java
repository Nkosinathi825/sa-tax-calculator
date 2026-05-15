package com.example.taxcalculator;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TaxCalculationRequest {

    @NotNull
    @DecimalMin(value = "0", message = "grossAnnual must be zero or positive")
    private BigDecimal grossAnnual;

    @Min(value = 0, message = "age must be zero or positive")
    @Max(value = 130, message = "age must be 130 or less")
    private int age;
}
