# Requirements — Phase 1: SARS Tax Engine (backend)

## Phase goal
Implement the server-side SARS 2024/2025 tax calculation logic and expose it via a single REST endpoint so that any client can obtain a full PAYE breakdown from a gross annual salary and age.

## In scope
- Model the 2024/2025 SARS income tax brackets (7 brackets, 18%–45%)
- Implement primary (R17,235), secondary (R9,444 for 65+), and tertiary (R3,145 for 75+) rebates
- Implement tax thresholds: below 65 → R95,750; 65–74 → R148,217; 75+ → R165,689
- Calculate annual income tax (gross annual) and monthly PAYE (annual ÷ 12)
- Calculate UIF employee contribution: 1% of gross monthly, capped at R177.12/month
- Expose POST /api/calculate accepting { grossAnnual, age } and returning full tax breakdown
- Unit-test the calculation service against known SARS examples

## Out of scope
- Medical aid tax credits (Phase 2)
- Retirement annuity deductions
- Multiple income sources or provisional tax
- Corporate/company tax

## Decisions & context
None recorded. Use the published 2024/2025 SARS tax tables as-is.
Rounding: use standard half-up rounding to two decimal places on all monetary outputs.

## References
- specs/mission.md — results must match SARS PAYE calculations to within a rounding margin
- specs/tech-stack.md — Spring Boot 3.x, Java 17, Lombok, spring-boot-starter-validation, JUnit 5
