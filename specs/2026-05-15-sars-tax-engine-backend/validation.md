# Validation — Phase 1: SARS Tax Engine (backend)

## Definition of done
All Phase 1 roadmap tasks are completed, unit tests pass, and POST /api/calculate returns a correct SARS-verified breakdown for a known salary and age.

## Checklist
- [ ] SarsTaxTables contains all 7 correct 2024/2025 brackets (verify base tax and rates)
- [ ] Primary rebate R17,235; secondary R9,444 (65+); tertiary R3,145 (75+)
- [ ] Income below threshold returns annualTax = 0.00
- [ ] TaxCalculationService.calculateTax passes all 8 unit test cases
- [ ] POST /api/calculate returns HTTP 200 with correct JSON body
- [ ] POST /api/calculate with negative grossAnnual returns HTTP 400
- [ ] UIF capped at R177.12/month regardless of salary
- [ ] mvn test passes with no failures

## How to run verification

Run the unit tests:
    cd tax-calculator
    mvn test

Start the server and test a known case manually:
    mvn spring-boot:run

In a second terminal (R500,000 gross, age 30):
    curl -s -X POST http://localhost:8080/api/calculate \
      -H "Content-Type: application/json" \
      -d "{\"grossAnnual\": 500000, \"age\": 30}" | jq .

Expected annualTax for R500,000 / age 30 (manual calculation):
    Bracket: R370,501–R512,800 → R77,362 + 31% of (R500,000 – R370,500)
           = R77,362 + 31% of R129,500
           = R77,362 + R40,145
           = R117,507
    Less primary rebate: R117,507 – R17,235 = R100,272.00
    monthlyPaye = R100,272 / 12 = R8,356.00
    UIF = min(R500,000/12 * 0.01, R177.12) = R177.12 (capped)

Test invalid input:
    curl -s -X POST http://localhost:8080/api/calculate \
      -H "Content-Type: application/json" \
      -d "{\"grossAnnual\": -1, \"age\": 30}"
    Expected: HTTP 400

## Merge criteria
All mvn test cases pass. POST /api/calculate returns the correct breakdown matching the manual SARS calculation above (within R1 rounding). No compilation errors.
