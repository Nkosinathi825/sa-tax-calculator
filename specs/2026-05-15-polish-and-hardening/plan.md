# Plan — Phase 4: Polish & Hardening

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Input validation
1. In TaxFormComponent, mark grossAnnual and age as touched on submit attempt so errors show immediately
2. Add a Validators.pattern or custom validator to reject non-numeric input in grossAnnual
3. Ensure all three mat-error messages are visible and specific:
   - grossAnnual: "Required", "Must be zero or more"
   - age: "Required", "Must be between 0 and 130"
   - medicalAidMembers: "Must be between 0 and 20"
4. Disable the Calculate button while the form is invalid (already done) and while loading

## Group 2 — API error handling
1. In TaxFormComponent, expand the error handler to distinguish network errors from server errors
2. Add an error state property (errorMessage: string | null) already present — ensure it shows in a mat-error or styled alert below the form
3. Clear the error message on each new submit attempt
4. Style the error banner using Angular Material's warn colour (red) so it is clearly visible

## Group 3 — Tax year label and SARS reference link
1. Add a "Rates applicable to the 2024/2025 tax year" note below the form card
2. Add a "View SARS tax tables" link pointing to the official SARS individual income tax rates page (https://www.sars.gov.za/tax-rates/income-tax/rates-of-tax-for-individuals/)
3. Display both the note and the link in the form card footer or below the results panel
4. Style the link subtly (mat-button or plain anchor with primary colour) so it does not compete with the Calculate button

## Notes
- All changes are confined to the Angular frontend; no backend changes needed.
- The SARS link opens in a new tab (target="_blank" rel="noopener").
- Mark form controls as touched on submit using Object.values(this.form.controls).forEach(c => c.markAsTouched()) so validation errors show before the user has interacted with every field.
