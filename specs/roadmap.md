# Roadmap

Phases are intentionally small — each should be completable in a single focused session.

## Phase 0 — Skeleton
- [ ] Rename Maven artifact from `untitled` to `tax-calculator` in `pom.xml`
- [ ] Scaffold Spring Boot app with `@SpringBootApplication` entry point
- [ ] Scaffold Angular app (`ng new tax-calculator-ui`) inside the repo
- [ ] Configure Angular proxy (`proxy.conf.json`) to forward `/api` to `localhost:8080`
- [ ] Confirm both apps start without errors (`mvn spring-boot:run` + `ng serve`)

## Phase 1 — SARS Tax Engine (backend)
- [ ] Model the 2024/2025 SARS income tax brackets (7 brackets, 18%–45%)
- [ ] Implement primary, secondary (65+), and tertiary (75+) rebates
- [ ] Implement tax thresholds (below 65, 65–74, 75+)
- [ ] Calculate annual income tax and monthly PAYE from gross annual salary
- [ ] Calculate UIF contribution (1% employee, capped at R177.12/month)
- [ ] Expose `POST /api/calculate` endpoint accepting `{ grossAnnual, age }` and returning full tax breakdown
- [ ] Unit-test the calculation service against known SARS examples

## Phase 2 — Medical Aid Credit (backend)
- [ ] Implement Section 6A medical tax credits (main member, first dependant, additional dependants)
- [ ] Add `medicalAidMembers` field to the calculation request DTO
- [ ] Apply credit as a reduction to tax payable (not a deduction from income)
- [ ] Unit-test credit scenarios

## Phase 3 — Angular Frontend
- [ ] Build a salary input form (gross annual income, age, medical aid dependants)
- [ ] Call `POST /api/calculate` on form submit
- [ ] Display results: gross income, taxable income, income tax, UIF, medical credit, net monthly pay, effective tax rate
- [ ] Show a breakdown of which SARS tax bracket applies
- [ ] Basic responsive layout using Angular Material

## Phase 4 — Polish & hardening
- [ ] Input validation (non-negative income, valid age, max dependants)
- [ ] Friendly error messages when the API is unreachable
- [ ] Display the SARS tax year the rates apply to (2024/2025)
- [ ] Add a link to the relevant SARS tax tables page for reference

## Phase 5 — Packaging
- [ ] Build Angular for production (`ng build --configuration production`)
- [ ] Configure Spring Boot to serve Angular static files from `src/main/resources/static`
- [ ] Single `mvn package` produces a self-contained JAR that serves the full app
- [ ] Update README with setup and run instructions

## Phase 6 — Dark Mode Toggle
- [ ] Add light / dark theme toggle button to the Angular toolbar
- [ ] Define SCSS-based light and dark Material themes in styles.scss
- [ ] Toggle a `.dark-theme` class on the body to switch themes at runtime
- [ ] Persist the user's preference in localStorage across page reloads

## Icebox (future ideas, not committed)
- Retirement annuity (RA) deduction calculator
- Travel allowance and car benefit tax
- Compare two salary scenarios side by side
- Annual salary-to-hourly rate converter
