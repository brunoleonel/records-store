# Records Store API
#### Records Store API  with cashback calculation

_***Powered by***:_

 * [Java8](https://openjdk.java.net/projects/jdk8/)
 * [Spring Boot 2.1](https://spring.io/projects/spring-boot)
 * [Flyway](https://flywaydb.org/)
 * [Spotify web API Java](https://github.com/thelinmichael/spotify-web-api-java) 

_***Running the project***:_

* Download the project 

* Run mvn install

* Run docker-compose -f docker/prod/docker-compose.yml up

* Build application with: mvn compile

* Execute the migrations with: mvn flyway:migrate -Dflyway.configFiles=flywaydb-prod.conf

_***Executing the tests***:_

* Run mvn compile

* Run mvn test jacoco:report

* The code coverage report will be generated at target/site/jacoco/index.html

Postman collection included for testing requests: **Records Store.postman_collection.json**



