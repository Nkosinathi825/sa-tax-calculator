package com.example.taxcalculator;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TaxCalculationService {

    private static final BigDecimal TWELVE = BigDecimal.valueOf(12);
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    public TaxCalculationResponse calculate(TaxCalculationRequest request) {
        BigDecimal grossAnnual = request.getGrossAnnual();
        int age = request.getAge();
        int medicalAidMembers = request.getMedicalAidMembers();

        BigDecimal medicalCreditMonthly = computeMedicalAidCreditMonthly(medicalAidMembers);
        BigDecimal medicalCreditAnnual = medicalCreditMonthly.multiply(TWELVE).setScale(2, RoundingMode.HALF_UP);

        BigDecimal annualTax = computeAnnualTax(grossAnnual, age, medicalCreditAnnual);
        BigDecimal monthlyPaye = annualTax.divide(TWELVE, 2, RoundingMode.HALF_UP);
        BigDecimal uifMonthly = computeUif(grossAnnual);
        BigDecimal grossMonthly = grossAnnual.divide(TWELVE, 2, RoundingMode.HALF_UP);
        BigDecimal netMonthly = grossMonthly.subtract(monthlyPaye).subtract(uifMonthly);
        BigDecimal effectiveTaxRate = grossAnnual.compareTo(ZERO) == 0
                ? ZERO
                : annualTax.divide(grossAnnual, 6, RoundingMode.HALF_UP)
                           .multiply(HUNDRED)
                           .setScale(2, RoundingMode.HALF_UP);

        return TaxCalculationResponse.builder()
                .grossAnnual(grossAnnual.setScale(2, RoundingMode.HALF_UP))
                .taxableIncome(grossAnnual.setScale(2, RoundingMode.HALF_UP))
                .annualTax(annualTax)
                .monthlyPaye(monthlyPaye)
                .uifMonthly(uifMonthly)
                .medicalAidCreditMonthly(medicalCreditMonthly)
                .medicalAidCreditAnnual(medicalCreditAnnual)
                .netMonthly(netMonthly.setScale(2, RoundingMode.HALF_UP))
                .effectiveTaxRate(effectiveTaxRate)
                .build();
    }

    private BigDecimal computeAnnualTax(BigDecimal grossAnnual, int age, BigDecimal medicalCreditAnnual) {
        BigDecimal threshold = thresholdFor(age);
        if (grossAnnual.compareTo(threshold) <= 0) {
            return ZERO.setScale(2, RoundingMode.HALF_UP);
        }

        TaxBracket bracket = SarsTaxTables.BRACKETS_2024_2025.stream()
                .filter(b -> b.contains(grossAnnual))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No bracket found for income: " + grossAnnual));

        BigDecimal amountAboveLower = grossAnnual.subtract(BigDecimal.valueOf(bracket.lowerBound() - 1));
        BigDecimal marginalComponent = amountAboveLower.multiply(bracket.marginalRate());
        BigDecimal grossTax = bracket.baseTax().add(marginalComponent);

        BigDecimal rebates = rebatesFor(age);
        BigDecimal netTax = grossTax.subtract(rebates).subtract(medicalCreditAnnual);

        return netTax.compareTo(ZERO) < 0
                ? ZERO.setScale(2, RoundingMode.HALF_UP)
                : netTax.setScale(2, RoundingMode.HALF_UP);
    }

    BigDecimal computeMedicalAidCreditMonthly(int members) {
        if (members <= 0) return ZERO.setScale(2, RoundingMode.HALF_UP);
        BigDecimal monthly = SarsTaxTables.MEDICAL_CREDIT_MAIN_MEMBER;
        if (members >= 2) monthly = monthly.add(SarsTaxTables.MEDICAL_CREDIT_FIRST_DEPENDANT);
        if (members >= 3) {
            BigDecimal additional = SarsTaxTables.MEDICAL_CREDIT_ADDITIONAL_DEPENDANT
                    .multiply(BigDecimal.valueOf(members - 2));
            monthly = monthly.add(additional);
        }
        return monthly.setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal computeUif(BigDecimal grossAnnual) {
        BigDecimal grossMonthly = grossAnnual.divide(TWELVE, 10, RoundingMode.HALF_UP);
        BigDecimal cappedMonthly = grossMonthly.min(SarsTaxTables.UIF_MONTHLY_REMUNERATION_CAP);
        return cappedMonthly.multiply(SarsTaxTables.UIF_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal thresholdFor(int age) {
        if (age >= 75) return SarsTaxTables.THRESHOLD_75_PLUS;
        if (age >= 65) return SarsTaxTables.THRESHOLD_65_TO_74;
        return SarsTaxTables.THRESHOLD_BELOW_65;
    }

    private BigDecimal rebatesFor(int age) {
        BigDecimal total = SarsTaxTables.REBATE_PRIMARY;
        if (age >= 65) total = total.add(SarsTaxTables.REBATE_SECONDARY);
        if (age >= 75) total = total.add(SarsTaxTables.REBATE_TERTIARY);
        return total;
    }
}
