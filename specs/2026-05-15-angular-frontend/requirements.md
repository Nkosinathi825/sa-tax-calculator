# Requirements — Phase 3: Angular Frontend

## Phase goal
Build the Angular UI so that a user can enter their salary details and immediately see a full SARS tax breakdown, making the calculator usable end-to-end in a browser.

## In scope
- Salary input form with three fields: gross annual income, age, medical aid dependants
- Call POST /api/calculate on form submit via Angular HttpClient
- Display results: gross income, taxable income, income tax, UIF, medical credit, net monthly pay, effective tax rate
- Show which SARS tax bracket applies to the entered income
- Basic responsive layout using Angular Material (Indigo/Pink theme already configured)

## Out of scope
- Input validation error messages beyond required/min/max (Phase 4)
- Friendly API-unreachable error handling (Phase 4)
- SARS tax year label and link (Phase 4)
- Angular unit tests for the UI components (not required for this phase's done signal)

## Decisions & context
None recorded. Use Angular Material with the Indigo/Pink theme already configured in angular.json.
The bracket breakdown is computed client-side from the grossAnnual value using the same 2024/2025 bracket thresholds — no backend change required.

## References
- specs/mission.md — user enters a gross annual salary and immediately sees income tax, UIF, net pay, and effective tax rate
- specs/tech-stack.md — Angular 16, Angular Reactive Forms, HttpClient, Angular Material, proxy.conf.json forwards /api to localhost:8080
