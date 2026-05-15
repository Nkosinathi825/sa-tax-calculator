# Validation — Phase 0: Skeleton

## Definition of done
All five roadmap tasks are completed and both the Spring Boot backend and Angular frontend start without errors on their respective ports.

## Checklist
- [ ] `tax-calculator/pom.xml` has artifactId `tax-calculator` and groupId `com.example.taxcalculator`
- [ ] `mvn spring-boot:run` (from `tax-calculator/`) starts without errors on port 8080
- [ ] `GET http://localhost:8080/api/health` returns HTTP 200 with body `{ "status": "ok" }`
- [ ] `tax-calculator-ui/` exists and contains a valid Angular 16 project scaffold
- [ ] `tax-calculator-ui/proxy.conf.json` exists and maps `/api` to `http://localhost:8080`
- [ ] `angular.json` references `proxy.conf.json` under serve options
- [ ] `ng serve` (from `tax-calculator-ui/`) starts without errors on port 4200
- [ ] Browser at `http://localhost:4200` shows the default Angular welcome page

## How to run verification

Backend:
    cd tax-calculator
    mvn spring-boot:run

In a second terminal, test the health endpoint:
    curl http://localhost:8080/api/health

Frontend (in a third terminal):
    cd tax-calculator-ui
    ng serve

Open http://localhost:4200 in a browser and confirm the Angular default page loads.

To test the proxy is wired correctly, the Angular dev server at port 4200 should forward
    GET http://localhost:4200/api/health
to the Spring Boot server and return `{ "status": "ok" }`.

## Merge criteria
Both servers must start without errors and the health endpoint must respond correctly before a PR is opened. No failing tests. No compilation errors in either the Java or TypeScript build.
