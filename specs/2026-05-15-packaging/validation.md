# Validation — Phase 5: Packaging

## Definition of done
A single mvn package command in tax-calculator/ produces a self-contained JAR; running that JAR with java -jar serves both the API and the Angular UI on port 8080.

## Checklist
- [ ] angular.json outputPath points to ../tax-calculator/src/main/resources/static
- [ ] tax-calculator/.gitignore excludes /src/main/resources/static/
- [ ] mvn package (from tax-calculator/) completes with BUILD SUCCESS
- [ ] target/tax-calculator-1.0-SNAPSHOT.jar exists after the build
- [ ] java -jar target/tax-calculator-1.0-SNAPSHOT.jar starts on port 8080 without errors
- [ ] http://localhost:8080 serves the Angular UI (form and toolbar visible)
- [ ] POST http://localhost:8080/api/calculate returns a correct tax breakdown
- [ ] http://localhost:8080/api/health returns { "status": "ok" }
- [ ] README.md exists at the repo root with build and run instructions

## How to run verification

Build the JAR (this also builds Angular):
    cd tax-calculator
    mvn package -DskipTests

Run the JAR:
    java -jar target/tax-calculator-1.0-SNAPSHOT.jar

Open http://localhost:8080 in a browser — the Angular UI should load.

Test the API directly:
    curl -s http://localhost:8080/api/health
    curl -s -X POST http://localhost:8080/api/calculate \
      -H "Content-Type: application/json" \
      -d "{\"grossAnnual\": 500000, \"age\": 30, \"medicalAidMembers\": 0}"

Test the full build including tests:
    mvn package

## Merge criteria
mvn package completes successfully, the JAR starts and serves the Angular UI on port 8080, and the health and calculate endpoints respond correctly. README.md is present at the repo root.
