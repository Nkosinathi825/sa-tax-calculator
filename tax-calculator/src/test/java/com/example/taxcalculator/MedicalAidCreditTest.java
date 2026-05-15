package com.example.taxcalculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MedicalAidCreditTest {

    private TaxCalculationService service;

    @BeforeEach
    void setUp() {
        service = new TaxCalculationService();
    }

    // --- monthly credit formula ---

    @Test
    void zeroMembers_creditIsZero() {
        assertThat(service.computeMedicalAidCreditMonthly(0)).isEqualByComparingTo("0.00");
    }

    @Test
    void oneMember_creditIsR364() {
        assertThat(service.computeMedicalAidCreditMonthly(1)).isEqualByComparingTo("364.00");
    }

    @Test
    void twoMembers_creditIsR728() {
        assertThat(service.computeMedicalAidCreditMonthly(2)).isEqualByComparingTo("728.00");
    }

    @Test
    void threeMembers_creditIsR974() {
        // R364 + R364 + R246 = R974
        assertThat(service.computeMedicalAidCreditMonthly(3)).isEqualByComparingTo("974.00");
    }

    @Test
    void fiveMembers_creditIsR1466() {
        // R364 + R364 + 3*R246 = R728 + R738 = R1466
        assertThat(service.computeMedicalAidCreditMonthly(5)).isEqualByComparingTo("1466.00");
    }

    // --- credit applied to tax payable ---

    @Test
    void zeroMembers_annualTaxUnchangedFromPhase1Baseline() {
        TaxCalculationResponse result = calculate(500_000, 30, 0);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("100272.00");
        assertThat(result.getMedicalAidCreditAnnual()).isEqualByComparingTo("0.00");
    }

    @Test
    void threeMedicalMembers_creditDeductedFromTax() {
        // R500,000 age 30: annualTax without credit = R100,272
        // Annual credit = R974 * 12 = R11,688
        // Expected annualTax = R100,272 - R11,688 = R88,584.00
        TaxCalculationResponse result = calculate(500_000, 30, 3);
        assertThat(result.getMedicalAidCreditAnnual()).isEqualByComparingTo("11688.00");
        assertThat(result.getAnnualTax()).isEqualByComparingTo("88584.00");
    }

    @Test
    void creditExceedsTaxPayable_annualTaxFlooredToZero() {
        // R100,000, age 30: without credit, annualTax > 0 but small
        // With 10 medical members: monthly = R364+R364+8*R246 = R2,696 → annual R32,352
        // This should exceed the tax, so annualTax = 0
        TaxCalculationResponse result = calculate(100_000, 30, 10);
        assertThat(result.getAnnualTax()).isEqualByComparingTo("0.00");
        assertThat(result.getMedicalAidCreditAnnual()).isGreaterThan(BigDecimal.ZERO);
    }

    @Test
    void medicalCreditMonthlyMatchesAnnualDividedBy12() {
        TaxCalculationResponse result = calculate(500_000, 30, 3);
        BigDecimal expectedAnnual = result.getMedicalAidCreditMonthly()
                .multiply(BigDecimal.valueOf(12))
                .setScale(2, java.math.RoundingMode.HALF_UP);
        assertThat(result.getMedicalAidCreditAnnual()).isEqualByComparingTo(expectedAnnual);
    }

    @Test
    void monthlyPayeReflectsCreditReduction() {
        TaxCalculationResponse result = calculate(500_000, 30, 3);
        // monthlyPaye = annualTax / 12
        BigDecimal expectedPaye = result.getAnnualTax()
                .divide(BigDecimal.valueOf(12), 2, java.math.RoundingMode.HALF_UP);
        assertThat(result.getMonthlyPaye()).isEqualByComparingTo(expectedPaye);
    }

    private TaxCalculationResponse calculate(double grossAnnual, int age, int medicalAidMembers) {
        TaxCalculationRequest request = new TaxCalculationRequest();
        request.setGrossAnnual(BigDecimal.valueOf(grossAnnual));
        request.setAge(age);
        request.setMedicalAidMembers(medicalAidMembers);
        return service.calculate(request);
    }
}
