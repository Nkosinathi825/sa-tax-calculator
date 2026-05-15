# Validation — Phase 3: Angular Frontend

## Definition of done
ng serve starts without errors, the salary form submits to the running Spring Boot backend, and the results panel displays a correct breakdown in the browser.

## Checklist
- [ ] ng serve starts on port 4200 with no compilation errors
- [ ] Form renders with three input fields (gross annual income, age, medical aid members) and a Calculate button
- [ ] Submitting the form with valid data calls POST /api/calculate and displays results
- [ ] Results panel shows: gross income, taxable income, income tax, UIF, medical credit, net monthly pay, effective tax rate
- [ ] Results panel shows which SARS tax bracket applies
- [ ] Submitting with grossAnnual = 0 shows zero tax results without an error
- [ ] Layout is usable on a narrow (mobile) viewport

## How to run verification

Start the backend in one terminal:
    cd tax-calculator
    mvn spring-boot:run

Start the frontend in a second terminal:
    cd tax-calculator-ui
    ng serve

Open http://localhost:4200 in a browser.

Test case 1 — middle earner:
    grossAnnual: 500000, age: 30, medicalAidMembers: 0
    Expected: annualTax ~R100,272, monthlyPaye ~R8,356, bracket "31%"

Test case 2 — with medical aid:
    grossAnnual: 500000, age: 30, medicalAidMembers: 3
    Expected: annualTax ~R88,584, medicalAidCreditMonthly R974

Test case 3 — below threshold:
    grossAnnual: 90000, age: 30
    Expected: annualTax R0.00, netMonthly ~R7,323

## Merge criteria
The browser smoke test passes for all three test cases above. ng serve produces no compilation errors. The backend tests still pass (mvn test).
