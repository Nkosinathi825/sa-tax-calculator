# Requirements — Phase 4: Polish & Hardening

## Phase goal
Harden the Angular frontend with proper input validation, a friendly API-unreachable error state, a visible tax year label, and a reference link to the SARS tax tables so the app is trustworthy and usable in production-like conditions.

## In scope
- Input validation with user-facing error messages: non-negative income, valid age (0-130), medical aid members (0-20), all required fields marked
- Friendly error banner when the API is unreachable (network error or non-2xx response)
- Display the SARS tax year the rates apply to (2024/2025) prominently in the UI
- Add a link to the relevant SARS tax tables page for reference

## Out of scope
- Backend validation changes (already handled by spring-boot-starter-validation in Phase 1)
- Angular unit tests for validation (not required for this phase's done signal)
- Dark mode, theming changes beyond what is needed for the error banner
- Packaging / JAR changes (Phase 5)

## Decisions & context
None recorded. Use existing Angular Material form field error patterns already established in Phase 3.
The SARS link should point to the official SARS individual income tax page.

## References
- specs/mission.md — tax brackets and rebates must reflect the current SARS tax year (2024/2025); results must be trustworthy
- specs/tech-stack.md — Angular 16, Angular Material, Reactive Forms; no new dependencies required
