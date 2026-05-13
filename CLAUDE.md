# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Java 17 Maven project for a tax calculator (v3). The IntelliJ IDEA project lives under `untitled/` — source code goes in `untitled/src/main/java/` and tests in `untitled/src/test/java/`.

## Build & Run

All Maven commands run from the `untitled/` directory:

```powershell
cd untitled

# Compile
mvn compile

# Run all tests
mvn test

# Run a single test class
mvn test -Dtest=ClassName

# Package
mvn package
```

## Project Structure

```
untitled/
  pom.xml          # Maven build — Java 17, groupId org.example, artifactId untitled
  src/
    main/java/     # Application source (to be created)
    test/java/     # Test source (to be created)
```

No dependencies have been added yet. When adding JUnit or other test libraries, add them to `untitled/pom.xml`.

## Frontend — Angular

The frontend will be built with Angular. Installed versions:

| Tool | Version |
|------|---------|
| Angular CLI | 16.2.16 |
| Node | 24.14.1 |
| npm | 11.11.0 |

> Note: Node 24 is flagged as "Unsupported" by Angular CLI 16. Consider upgrading to Angular 17+ or using Node 18/20 LTS for a supported pairing.

Common Angular commands (run from the Angular project directory once scaffolded):

```powershell
# Create a new Angular app
ng new tax-calculator-ui

# Serve with live reload
ng serve

# Build for production
ng build --configuration production

# Run unit tests
ng test

# Run a single spec file
ng test --include="**/foo.component.spec.ts"

# Generate a component / service / pipe
ng generate component components/tax-form
ng generate service services/tax
```
