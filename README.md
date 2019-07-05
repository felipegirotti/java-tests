# Repos - Exemplify Tests
A simple application to list repositories of user in the github and mark it a favorite  
To exemplify tests, unit and integration tests using the Spring Boot.

## Run
We are using the env variables, to use in your ide please import the envars in the file `.env.example`,     
for running in the cli `export $(cat .env.example | xargs)`

## Run Tests
`mvn test`

## Run Sonar
First you need run the tests after `mvn sonar:sonar`

