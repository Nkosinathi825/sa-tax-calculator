# Plan — Phase 1: SARS Tax Engine (backend)

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Tax data models
1. Create TaxBracket record: lowerBound, upperBound (Long.MAX_VALUE for the top bracket), baseTax, marginalRate
2. Create SarsTaxTables class with static 2024/2025 bracket list, rebate constants, and threshold constants
3. Bracket data (annual, ZAR):
   - R1–R237,100: R0 base + 18%
   - R237,101–R370,500: R42,678 base + 26% above R237,100
   - R370,501–R512,800: R77,362 base + 31% above R370,500
   - R512,801–R673,000: R121,475 base + 36% above R512,800
   - R673,001–R857,900: R179,147 + 39% above R673,000
   - R857,901–R1,817,000: R251,258 + 41% above R857,900
   - R1,817,001+: R644,489 + 45% above R1,817,000
4. Rebate constants: PRIMARY=17235, SECONDARY=9444, TERTIARY=3145
5. Threshold constants: BELOW_65=95750, AGE_65_TO_74=148217, AGE_75_PLUS=165689

## Group 2 — Calculation service
1. Create TaxCalculationRequest DTO: grossAnnual (BigDecimal, @NotNull, @DecimalMin("0")), age (int, @Min(0) @Max(130))
2. Create TaxCalculationResponse DTO: grossAnnual, taxableIncome, annualTax, monthlyPaye, uifMonthly, netMonthly, effectiveTaxRate (BigDecimal fields, all rounded to 2 dp)
3. Create TaxCalculationService annotated @Service
4. Implement calculateTax(TaxCalculationRequest): find bracket, compute marginalTax, subtract applicable rebates, floor to zero if below threshold
5. Implement UIF: min(grossAnnual/12 * 0.01, 177.12)
6. Derive netMonthly = grossAnnual/12 - monthlyPaye - uifMonthly
7. Derive effectiveTaxRate = annualTax / grossAnnual * 100 (zero if grossAnnual is zero)

## Group 3 — REST endpoint
1. Create TaxCalculationController annotated @RestController, @RequestMapping("/api")
2. Implement POST /api/calculate: accept @Valid @RequestBody TaxCalculationRequest, return TaxCalculationResponse
3. Add @Validated to the controller class so Bean Validation fires on the request body

## Group 4 — Unit tests
1. Write TaxCalculationServiceTest using JUnit 5
2. Test case: income below threshold (R90,000, age 30) → annualTax = 0
3. Test case: income just above threshold (R100,000, age 30) → verify bracket and rebate math
4. Test case: middle-bracket earner (R500,000, age 30) → verify against manual SARS calculation
5. Test case: high earner (R2,000,000, age 30) → top bracket
6. Test case: age 65 → secondary rebate applied
7. Test case: age 75 → primary + secondary + tertiary rebates applied
8. Test case: UIF cap (grossAnnual = R600,000 → monthly R50,000 → UIF = R177.12 capped)

## Notes
- BigDecimal throughout for monetary values; use HALF_UP rounding at two decimal places.
- The bracket upper bound for the last bracket is represented as Long.MAX_VALUE to avoid a magic number.
- TaxCalculationRequest and TaxCalculationResponse should use Lombok @Data / @Builder to reduce boilerplate.
- Keep SarsTaxTables as a plain class with static fields — no Spring bean needed.
