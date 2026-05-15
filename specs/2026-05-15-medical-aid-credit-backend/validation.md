# Validation — Phase 2: Medical Aid Credit (backend)

## Definition of done
All Phase 2 roadmap tasks are completed, unit tests pass, and POST /api/calculate returns a correct tax breakdown with medical aid credit applied as a reduction to tax payable.

## Checklist
- [ ] SarsTaxTables has correct 2024/2025 Section 6A credit constants (R364, R364, R246)
- [ ] TaxCalculationRequest accepts medicalAidMembers (0–20), defaulting to 0
- [ ] TaxCalculationResponse includes medicalAidCreditMonthly and medicalAidCreditAnnual
- [ ] Credit is applied as a reduction to annualTax after rebates, floored to zero
- [ ] Existing Phase 1 tests still pass (no regressions)
- [ ] All new credit scenario tests pass
- [ ] mvn test passes with no failures

## How to run verification

Run all tests:
    cd tax-calculator
    mvn test

Start the server and test a known case (R500,000, age 30, 3 medical aid members):
    mvn spring-boot:run

In a second terminal:
    curl -s -X POST http://localhost:8080/api/calculate \
      -H "Content-Type: application/json" \
      -d "{\"grossAnnual\": 500000, \"age\": 30, \"medicalAidMembers\": 3}" | jq .

Expected values:
    medicalAidCreditMonthly: 974.00
    medicalAidCreditAnnual: 11688.00
    annualTax: 100272.00 - 11688.00 = 88584.00
    monthlyPaye: 88584.00 / 12 = 7382.00

Test that omitting medicalAidMembers returns the same result as medicalAidMembers = 0:
    curl -s -X POST http://localhost:8080/api/calculate \
      -H "Content-Type: application/json" \
      -d "{\"grossAnnual\": 500000, \"age\": 30}" | jq .annualTax
    Expected: 100272.00

Test credit-exceeds-tax scenario (R100,000, age 30, 10 members):
    Expected: annualTax = 0.00, medicalAidCreditAnnual > 0

## Merge criteria
All mvn test cases pass including regressions. POST /api/calculate returns correct breakdown matching the manual SARS calculation above (within R1 rounding). No compilation errors.
