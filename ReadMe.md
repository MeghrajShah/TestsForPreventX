**This project is a Demo of UI and API Tests**

**UI Cucumber tests located at** - src/test/java/ui/cucumber/features/UISauceLabsShopping.feature
- UI Step Definitions file located at - src/test/java/ui/cucumber/steps/UIStepDefinitions.java
- Non cucumber JUnit UI Tests located at - src/test/java/ui/SauceLabsTest.java

**API Cucumber Tests located at** - src/test/java/api/cucumber/features/ApiTests.feature
- Tests cover a Get and POST endpoint , does a product search in 2 styles

**Running tests**

- Right click feature file and click Run 'Feature <FileName>'
OR
- On terminal - use below command > 
  - **To run API Tests** > ./gradlew test -Dcucumber.filter.tags="@API"
  - **To run UI Tests** > ./gradlew test -Dcucumber.filter.tags="@UI"
- To retry failed scenarios - use command > ./gradlew test -Dcucumber.retry.maxAttempts=3