# Validation — Phase 4: Polish & Hardening

## Definition of done
Manual browser smoke test passes: validation errors appear for bad input, a friendly message shows when the backend is down, and the tax year label plus SARS link are visible on the page.

## Checklist
- [ ] Submitting the form with an empty grossAnnual shows "Required" error immediately
- [ ] Entering a negative grossAnnual shows "Must be zero or more"
- [ ] Entering age 200 shows "Must be between 0 and 130"
- [ ] Entering medicalAidMembers 25 shows "Must be between 0 and 20"
- [ ] Stopping the backend and submitting shows a friendly API-unreachable error message
- [ ] "2024/2025 tax year" label is visible on the page
- [ ] SARS link is present and opens the correct page in a new tab
- [ ] Valid input still calculates and displays results correctly (no regressions)

## How to run verification

Start only the frontend (backend intentionally stopped):
    cd tax-calculator-ui
    ng serve

Open http://localhost:4200 and:
1. Click Calculate without filling any fields — verify "Required" errors appear
2. Enter grossAnnual = -100 — verify "Must be zero or more"
3. Enter age = 200 — verify age error
4. Enter medicalAidMembers = 25 — verify members error
5. Confirm the "2024/2025 tax year" text is visible
6. Click the SARS link — confirm it opens https://www.sars.gov.za/tax-rates/income-tax/rates-of-tax-for-individuals/ in a new tab
7. With backend still stopped, enter valid values and submit — confirm friendly error message appears

Start the backend and re-test:
    cd tax-calculator
    mvn spring-boot:run

8. Enter valid values — confirm results still display correctly

## Merge criteria
All eight checklist items pass in the browser. ng build --configuration development produces no compilation errors. Backend mvn test still passes.
