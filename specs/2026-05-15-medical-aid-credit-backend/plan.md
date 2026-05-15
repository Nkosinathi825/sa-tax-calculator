# Plan — Phase 2: Medical Aid Credit (backend)

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Credit constants
1. Add Section 6A monthly credit constants to SarsTaxTables:
   MEDICAL_CREDIT_MAIN_MEMBER = R364
   MEDICAL_CREDIT_FIRST_DEPENDANT = R364
   MEDICAL_CREDIT_ADDITIONAL_DEPENDANT = R246

## Group 2 — DTO and service updates
1. Add medicalAidMembers field to TaxCalculationRequest: int, @Min(0) @Max(20), default 0
2. Add medicalAidCreditMonthly and medicalAidCreditAnnual fields (BigDecimal) to TaxCalculationResponse
3. Add a private computeMedicalAidCredit(int members) method to TaxCalculationService:
   - 0 members: return zero
   - 1 member: R364/month
   - 2 members: R364 + R364 = R728/month
   - 3+ members: R364 + R364 + (members - 2) * R246/month
   - Annual credit = monthly * 12
4. In computeAnnualTax, apply the annual credit as a further reduction after rebates (floor to zero)
5. Pass medicalAidCreditMonthly and medicalAidCreditAnnual into TaxCalculationResponse builder

## Group 3 — Unit tests
1. Add test cases to TaxCalculationServiceTest (or a new MedicalAidCreditTest class):
   - 0 members: credit is zero, annualTax unchanged from Phase 1 baseline
   - 1 member: credit R364/month = R4,368/year applied to tax
   - 2 members: credit R728/month = R8,736/year
   - 3 members: credit R974/month = R11,688/year
   - 5 members: credit R364+R364+(3*R246) = R1,466/month = R17,592/year
   - Credit exceeds tax payable (low income, high dependants): annualTax floored to zero, credit is still reported correctly

## Notes
- Keep computeMedicalAidCredit pure (no side effects) so it is easy to test in isolation.
- The monthly credit amounts are fixed statutory values, not derived from actual premiums paid.
- medicalAidMembers defaults to 0 so existing API callers that omit the field are unaffected.
