# Requirements — Phase 2: Medical Aid Credit (backend)

## Phase goal
Add Section 6A medical tax credit support to the backend so that taxpayers who contribute to a medical aid scheme see a reduction in their tax payable, making the calculator accurate for the majority of South African salaried employees.

## In scope
- Implement 2024/2025 Section 6A medical tax credit rates: R364/month for main member, R364/month for first dependant, R246/month for each additional dependant
- Add medicalAidMembers field (integer, minimum 0) to TaxCalculationRequest DTO
- Compute the annual credit (monthly credit * 12) and apply it as a reduction to annual tax payable after rebates
- Floor tax payable to zero if the credit exceeds remaining tax (no negative tax)
- Add medicalAidCreditMonthly and medicalAidCreditAnnual fields to TaxCalculationResponse DTO
- Unit-test all credit scenarios: zero members, main member only, main + one dependant, main + multiple dependants, credit exceeding tax payable

## Out of scope
- Section 6B additional medical expense credits (4x excess contributions)
- Employer-paid medical aid contributions
- Angular frontend changes (Phase 3)

## Decisions & context
None recorded. Use 2024/2025 SARS Section 6A credit rates as-is.
Credit is a reduction to tax payable, not a deduction from taxable income. If medicalAidMembers is 0 or omitted, the credit is zero and the response is unchanged from Phase 1 behaviour.

## References
- specs/mission.md — results must match SARS PAYE calculations to within a rounding margin
- specs/tech-stack.md — Spring Boot 3.x, Java 17, Lombok, JUnit 5; no new dependencies required
