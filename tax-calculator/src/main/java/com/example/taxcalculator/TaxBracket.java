package com.example.taxcalculator;

import java.math.BigDecimal;

record TaxBracket(long lowerBound, long upperBound, BigDecimal baseTax, BigDecimal marginalRate) {

    boolean contains(BigDecimal income) {
        return income.compareTo(BigDecimal.valueOf(lowerBound)) >= 0
                && income.compareTo(BigDecimal.valueOf(upperBound)) <= 0;
    }
}
