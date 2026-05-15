# SA Tax Calculator

A web-based South African PAYE and UIF tax calculator using the 2024/2025 SARS tax tables.
Enter a gross annual salary, age, and medical aid members to get an instant breakdown of income tax, UIF, medical aid credit, and net monthly take-home pay.

## Prerequisites

| Tool | Version |
|------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| Node | 20+ |
| npm | 9+ |

## Quick start — single JAR

Build everything (Angular production build + Spring Boot JAR) with one command:

```bash
cd tax-calculator
mvn package
```

Run the self-contained JAR:

```bash
java -jar target/tax-calculator-1.0-SNAPSHOT.jar
```

Open http://localhost:8080 in your browser.

The JAR serves both the REST API and the Angular UI — no separate frontend server needed.

## Development mode — two servers

Run the backend with live reload:

```bash
cd tax-calculator
mvn spring-boot:run
```

In a second terminal, run the Angular dev server with proxy to the backend:

```bash
cd tax-calculator-ui
ng serve
```

Open http://localhost:4200. The Angular dev server proxies `/api` requests to `http://localhost:8080`.

## API

| Method | Path | Description |
|--------|------|-------------|
| GET | /api/health | Liveness check |
| POST | /api/calculate | Calculate PAYE, UIF, and medical aid credit |

### POST /api/calculate

Request body:

```json
{
  "grossAnnual": 500000,
  "age": 30,
  "medicalAidMembers": 2
}
```

Response:

```json
{
  "grossAnnual": 500000.00,
  "taxableIncome": 500000.00,
  "annualTax": 91536.00,
  "monthlyPaye": 7628.00,
  "uifMonthly": 177.12,
  "medicalAidCreditMonthly": 728.00,
  "medicalAidCreditAnnual": 8736.00,
  "netMonthly": 33894.88,
  "effectiveTaxRate": 18.31
}
```

## Project structure

```
taxCalculator_v3/
  tax-calculator/          Spring Boot REST API (Java 17, Maven)
    src/main/java/         Application source
    src/test/java/         Unit tests
    pom.xml
  tax-calculator-ui/       Angular 16 frontend
    src/app/
      components/          TaxFormComponent, TaxResultsComponent
      models/              TypeScript interfaces
      services/            TaxCalculationService (HttpClient)
  specs/                   Phase specs (requirements, plan, validation)
  README.md
```

## Running tests

Backend unit tests:

```bash
cd tax-calculator
mvn test
```
