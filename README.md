* Run docker-compose -f docker/prod/docker-compose.yml up

* Build application with: mvn compile

* Execute the migrations with: mvn flyway:migrate -Dflyway.configFiles=flywaydb-prod.conf