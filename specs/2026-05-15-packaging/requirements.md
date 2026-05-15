# Requirements — Phase 5: Packaging

## Phase goal
Produce a single self-contained JAR from one mvn package command that serves both the Spring Boot REST API and the Angular frontend so the app can be run anywhere with just Java 17.

## In scope
- Configure Angular production build output path to land inside the Spring Boot classpath (src/main/resources/static)
- Add frontend-maven-plugin to pom.xml so mvn package triggers npm ci and ng build automatically
- Add a Spring MVC SPA fallback controller so Angular client-side routes work when the page is refreshed
- Add src/main/resources/static/ to .gitignore since it is a build artefact
- Update README.md at the repo root with prerequisites, build, and run instructions

## Out of scope
- Docker or containerisation
- CI/CD pipeline configuration
- Cloud deployment
- Environment-specific configuration profiles

## Decisions & context
None recorded. Standard approach: frontend-maven-plugin installs Node 20 LTS into target/, runs npm ci then ng build --configuration production, outputting to src/main/resources/static. Spring Boot auto-serves classpath:/static/. A SPA fallback controller forwards non-API, non-asset paths to index.html.

## References
- specs/mission.md — the app must be usable without requiring users to run two separate servers
- specs/tech-stack.md — Maven (backend build), Angular CLI / npm (frontend build), deployment target is local machine
