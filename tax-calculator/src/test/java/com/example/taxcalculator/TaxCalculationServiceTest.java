package com.example.taxcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class TaxCalculationServiceTest {

    private TaxCalculationService service;

    @BeforeEach
    void setUp() {
        service = new TaxCalculationService();
    }

    @Test
    void incomeZero_taxAndUifAreZero() {
        TaxCalculationResponse result = calculate(0, 30);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("0.00");
        assertThat(result.getUifMonthly()).isEqualByComparingTo("0.00");
    }

    @Test
    void incomeBelowThreshold_taxIsZero() {
        // R90,000 < R95,750 threshold for age < 65
        TaxCalculationResponse result = calculate(90_000, 30);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("0.00");
    }

    @Test
    void incomeJustAboveThreshold_taxIsGreaterThanZero() {
        // R100,000 > R95,750 threshold
        TaxCalculationResponse result = calculate(100_000, 30);
        assertThat(result.getAnnualTax()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void middleBracketEarner_R500000_age30() {
        // Bracket 3: R370,501–R512,800 → R77,362 + 31% of (income – R370,500)
        // = R77,362 + 0.31 * (R500,000 – R370,500)
        // = R77,362 + 0.31 * R129,500
        // = R77,362 + R40,145
        // = R117,507 – R17,235 (primary rebate) = R100,272.00
        TaxCalculationResponse result = calculate(500_000, 30);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("100272.00");
    }

    @Test
    void highEarner_R2000000_age30() {
        // Top bracket: R1,817,001+ → R644,489 + 45% of (R2,000,000 – R1,817,000)
        // = R644,489 + 0.45 * R183,000
        // = R644,489 + R82,350
        // = R726,839 – R17,235 (primary) = R709,604.00
        TaxCalculationResponse result = calculate(2_000_000, 30);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("709604.00");
    }

    @Test
    void age65_secondaryRebateApplied() {
        // R500,000, age 65: gross tax R117,507 – (R17,235 + R9,444) = R90,828.00
        TaxCalculationResponse result = calculate(500_000, 65);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("90828.00");
    }

    @Test
    void age75_allThreeRebatesApplied() {
        // R500,000, age 75: gross tax R117,507 – (R17,235 + R9,444 + R3,145) = R87,683.00
        TaxCalculationResponse result = calculate(500_000, 75);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("87683.00");
    }

    @Test
    void uifCappedAtR17712AnnualRemuneration() {
        // R600,000 gross → monthly R50,000 → above UIF cap R17,712 → UIF = R17,712 * 1% / 12... no
        // UIF = min(R50,000, R17,712) * 1% = R17,712 * 0.01 = R177.12
        TaxCalculationResponse result = calculate(600_000, 30);
        assertThat(result.getUifMonthly()).isEqualByComparingTo("177.12");
    }

    @Test
    void uifNotCappedForLowIncome() {
        // R120,000 / 12 = R10,000/month → below UIF cap → UIF = R10,000 * 1% = R100.00
        TaxCalculationResponse result = calculate(120_000, 30);
        assertThat(result.getUifMonthly()).isEqualByComparingTo("100.00");
    }

    @Test
    void effectiveTaxRateIsCorrect() {
        // R500,000 / age 30 → annualTax R100,272 / R500,000 * 100 = 20.05%
        TaxCalculationResponse result = calculate(500_000, 30);
        assertThat(result.getEffectiveTaxRate())
                .usingComparator(BigDecimal::compareTo)
                .isEqualByComparingTo("20.05");
    }

    @Test
    void netMonthlyIsGrossMinusPayeMinusUif() {
        TaxCalculationResponse result = calculate(500_000, 30);
        BigDecimal expected = result.getGrossAnnual()
                .divide(BigDecimal.valueOf(12), 2, java.math.RoundingMode.HALF_UP)
                .subtract(result.getMonthlyPaye())
                .subtract(result.getUifMonthly());
        assertThat(result.getNetMonthly()).isEqualByComparingTo(expected);
    }

    private TaxCalculationResponse calculate(double grossAnnual, int age) {
        TaxCalculationRequest request = new TaxCalculationRequest();
        request.setGrossAnnual(BigDecimal.valueOf(grossAnnual));
        request.setAge(age);
        return service.calculate(request);
    }
}
