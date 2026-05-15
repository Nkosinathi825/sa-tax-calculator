# Plan — Phase 0: Skeleton

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Maven backend setup
1. Rename the Maven project folder from `untitled/` to `tax-calculator/`
2. Update `pom.xml`: set `artifactId` to `tax-calculator`, `groupId` to `com.example.taxcalculator`, and `name` to `tax-calculator`
3. Add `spring-boot-starter-web` and `spring-boot-starter-validation` and `lombok` dependencies to `pom.xml`
4. Add the `spring-boot-maven-plugin` to `pom.xml` so `mvn spring-boot:run` works
5. Create the package directory `src/main/java/com/example/taxcalculator/`
6. Write `TaxCalculatorApplication.java` with `@SpringBootApplication` and a `main` method
7. Write a minimal `HealthController.java` with `GET /api/health` returning `{ "status": "ok" }`

## Group 2 — Angular frontend scaffold
1. Run `ng new tax-calculator-ui --routing=true --style=scss --skip-git` inside the repo root
2. Add Angular Material (`ng add @angular/material`) inside `tax-calculator-ui/`
3. Create `tax-calculator-ui/proxy.conf.json` to proxy `/api` to `http://localhost:8080`
4. Update `tax-calculator-ui/angular.json` to reference `proxy.conf.json` in the serve options

## Group 3 — Smoke-test verification
1. Run `mvn spring-boot:run` from `tax-calculator/` and confirm port 8080 starts
2. Confirm `GET http://localhost:8080/api/health` returns `{ "status": "ok" }`
3. Run `ng serve` from `tax-calculator-ui/` and confirm port 4200 loads the default Angular page

## Notes
- The Maven folder rename from `untitled/` to `tax-calculator/` must happen before editing `pom.xml` paths.
- `--skip-git` is required on `ng new` because the repo is already a git repository.
- Angular Material prompts for theme/animations during `ng add`; choose Indigo/Pink theme and enable animations for a sensible default.
- The proxy config must be wired into `angular.json` under `projects.tax-calculator-ui.architect.serve.options.proxyConfig`; `ng serve` does not pick up `proxy.conf.json` automatically.
