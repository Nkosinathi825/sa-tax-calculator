# Requirements — Phase 0: Skeleton

## Phase goal
Stand up a working monorepo with a runnable Spring Boot backend and Angular frontend so all future phases have a solid, versioned foundation to build on.

## In scope
- Rename the Maven artifact from `untitled` to `tax-calculator` in `pom.xml` and align the project folder name
- Scaffold a minimal Spring Boot 3 app with `@SpringBootApplication` entry point and a health-check endpoint
- Scaffold an Angular 16 app (`tax-calculator-ui`) inside the repo using the Angular CLI
- Configure an Angular proxy (`proxy.conf.json`) to forward `/api/*` requests to `http://localhost:8080`
- Confirm `mvn spring-boot:run` starts on port 8080 without errors
- Confirm `ng serve` starts on port 4200 without errors

## Out of scope
- Any SARS tax logic (Phase 1)
- Any Angular UI components beyond the default scaffold (Phase 3)
- Medical aid credits (Phase 2)
- Database, authentication, or deployment configuration

## Decisions & context
None recorded.

## References
- specs/mission.md — deliver a web-based SA tax calculator; this phase creates the runnable skeleton that all future phases extend
- specs/tech-stack.md — Java 17 / Spring Boot 3 backend on port 8080; Angular 16 / Angular CLI 16.2.16 frontend on port 4200; Maven for backend, npm for frontend
