# Plan — Phase 3: Angular Frontend

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Models and API service
1. Create src/app/models/tax.models.ts with TaxCalculationRequest and TaxCalculationResponse TypeScript interfaces
2. Create src/app/services/tax-calculation.service.ts with a calculate(request) method using HttpClient POST to /api/calculate
3. Import HttpClientModule in app.module.ts

## Group 2 — Salary input form
1. Create src/app/components/tax-form/tax-form.component.ts with a ReactiveForm (FormGroup)
2. Form fields: grossAnnual (required, min 0), age (required, 0-130), medicalAidMembers (0-20, default 0)
3. Create src/app/components/tax-form/tax-form.component.html using mat-card, mat-form-field, matInput, and mat-button
4. Create src/app/components/tax-form/tax-form.component.scss for any local styles
5. Emit a calculated TaxCalculationResponse via an Output event on successful API response
6. Show a loading indicator on the submit button while the request is in flight
7. Declare TaxFormComponent in app.module.ts

## Group 3 — Results display
1. Create src/app/components/tax-results/tax-results.component.ts accepting a TaxCalculationResponse Input
2. Create src/app/components/tax-results/tax-results.component.html using mat-card to display all response fields
3. Add a computeBracket(grossAnnual) helper that returns the applicable bracket label (e.g. "31% — R370,501 to R512,800")
4. Create src/app/components/tax-results/tax-results.component.scss
5. Declare TaxResultsComponent in app.module.ts

## Group 4 — App shell and wiring
1. Update app.module.ts to import all required Angular Material modules (MatToolbarModule, MatCardModule, MatFormFieldModule, MatInputModule, MatButtonModule, MatProgressSpinnerModule, MatDividerModule, ReactiveFormsModule)
2. Update app.component.ts to hold the TaxCalculationResponse result and a handler for the form's output event
3. Update app.component.html: mat-toolbar header, tax-form component, tax-results component (shown only when result is present)
4. Update src/styles.scss for a centred max-width layout and basic spacing

## Notes
- The proxy config (proxy.conf.json) already forwards /api to localhost:8080 — no CORS config needed.
- Angular Material modules must be imported in app.module.ts; they are not auto-imported.
- Use currency pipe (| currency:'ZAR':'symbol-narrow':'1.2-2') for monetary display and percent pipe for effective tax rate.
- The bracket breakdown is purely client-side — define a SARS_BRACKETS constant in the results component.
- Keep all components in src/app/components/ to avoid cluttering the app root.
