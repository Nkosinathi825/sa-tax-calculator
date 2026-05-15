package com.example.taxcalculator;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TaxCalculationResponse {

    private BigDecimal grossAnnual;
    private BigDecimal taxableIncome;
    private BigDecimal annualTax;
    private BigDecimal monthlyPaye;
    private BigDecimal uifMonthly;
    private BigDecimal netMonthly;
    private BigDecimal effectiveTaxRate;
}
