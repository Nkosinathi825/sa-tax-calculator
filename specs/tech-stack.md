# Tech Stack

## Runtime & language
- Backend: Java 17 / Spring Boot 3.x
- Frontend: TypeScript 5 / Angular 16

## Framework(s)
- Spring Boot (REST API, Jackson for JSON)
- Angular 16 (standalone components or NgModules, Angular CLI 16.2.16)
- Angular Reactive Forms for input handling

## Data storage
None — all tax calculations are stateless and performed in-memory on the backend. No database required for v1.

## Key libraries / dependencies
- `spring-boot-starter-web` — REST endpoints for tax calculation
- `spring-boot-starter-validation` — Input validation (JSR-380 annotations)
- `lombok` — Reduces Java boilerplate on DTOs and models
- `Angular HttpClient` — Calls the Spring Boot API from the frontend
- `Angular Material` — UI component library for clean, accessible forms and result cards

## Developer tooling
- Build: Maven (backend), Angular CLI / npm (frontend)
- Test runner: JUnit 5 + Mockito (backend), Jasmine + Karma (frontend)
- Node: 24.14.1 / npm: 11.11.0

## Deployment target
Local machine for v1. Backend runs on `http://localhost:8080`, frontend on `http://localhost:4200` with Angular CLI proxy to avoid CORS.

## Rationale
Spring Boot gives a straightforward way to encapsulate SARS tax logic server-side with easy unit testing of calculation rules, keeping the Angular frontend purely presentational. The stack matches the user's stated preference and existing tooling.
