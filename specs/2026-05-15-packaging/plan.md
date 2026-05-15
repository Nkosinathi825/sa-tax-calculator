# Plan — Phase 5: Packaging

Each group is a focused unit of work. Complete groups in order.

## Group 1 — Angular output path
1. In tax-calculator-ui/angular.json, change the build outputPath from "dist/tax-calculator-ui" to "../tax-calculator/src/main/resources/static"
2. In tax-calculator/.gitignore, add /src/main/resources/static/ so the generated frontend files are not committed
3. Create tax-calculator/src/main/resources/static/.gitkeep so the directory is tracked as empty before the first build

## Group 2 — Maven frontend integration
1. Add frontend-maven-plugin 1.15.0 to the <plugins> section of tax-calculator/pom.xml
2. Configure workingDirectory to ${project.basedir}/../tax-calculator-ui and installDirectory to ${project.build.directory}
3. Add three executions all bound to the generate-resources phase:
   - install-node-and-npm: installs Node v20.18.3 into target/
   - npm-ci: runs "npm ci" to install packages from the lockfile
   - ng-build: runs "npm run build" which invokes ng build --configuration production

## Group 3 — SPA fallback controller
1. Create SpaController.java in com.example.taxcalculator
2. Annotate with @Controller (not @RestController — it must return a view forward)
3. Map all paths matching /{path:[^\\.]*} to forward to /index.html
4. This ensures Angular routes such as /calculate work on page refresh without a 404

## Group 4 — README
1. Create README.md at the repo root (taxCalculator_v3/)
2. Include sections: Project overview, Prerequisites (Java 17, Maven, Node 20+, Angular CLI), Quick start (single JAR), Development mode (two servers), and Project structure

## Notes
- The frontend-maven-plugin downloads Node 20.18.3 into target/ on first build; subsequent builds reuse the cached download.
- npm ci requires package-lock.json to be present and in sync — it is committed so this is safe.
- The SPA controller must not intercept /api/** paths or paths containing a dot (static assets); the regex [^\.]* naturally excludes dotted paths.
- Only one README.md at the repo root is needed; do not create one inside tax-calculator/ or tax-calculator-ui/.
