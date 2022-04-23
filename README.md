## Building and running
1. Install Java 11 via SDKMan (https://sdkman.io/), if you do not already have it installed
2. Recommended IDE: https://www.jetbrains.com/idea/ (Community Edition is sufficient)

## Running Program
1. Run `./gradlew assemble`
2. Run Via Terminal `java -jar build/libs/hotel-test-0.1-all.jar $start $limit $sort`

## Running Help Program
1.Run Via Terminal `java -jar build/libs/hotel-test-0.1-all.jar -h`

## Running tests
### Via IDE
1. Right click `src/test`, and choose `Run all tests`

### Via CLI
1. Run `./gradlew test`
2. Reports will be generated in `build/reports/tests/test/index.html`