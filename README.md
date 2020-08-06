# Animal eshop

## How to run

1) Using Docker and Docker Compose:
    1) Build image with command `docker-compose build`.
    2) Deploy stack with `docker-compose up -d`.

1) Manually
    1) Compile using command `mvn clean package`.
    1) Run the compiled artifact `target/animal-eshop-0.0.1-SNAPSHOT.jar`.
        1) Run with embedded H2 in-memory DB: `java -jar target/animal-eshop-0.0.1-SNAPSHOT.jar`
        1) Run with MariaDB using four favorite externalized configuration source (e.g. command line parameters): 
           ```bash
           java -jar target/animal-eshop-0.0.1-SNAPSHOT.jar \
               -Dspring.datasource.url=jdbc:mariadb://<host>:<port>/<db_name> \
               -Dspring.datasource.username=<username> \
               -Dspring.datasource.password=<password>
            ```

## Thoughts

- usage of HTTP Basic authentication for purpose of demo
  - in real world app I would use oAuth2 or JWT based auth with checks for token revocation  

- I would argue against requiring username in addition to email
  - email already serves as unique identifier and is required
  - unless users interact with each other, protecting their email from each other is not an issue
  - requiring more data from the user and requiring them to think up a unique username, 
    when their desired one can be already be taken, creates more friction in 
    user onboarding process, leading to lower conversion rates

- use of `JpaRepository.getOne(id)` creates proxy instead of querying the DB

- in this setup method parameters constraints violations are not correctly serialized;
  if this was fixed many more tests would be possible
  
- I always try to create one test for intended use case, and one for each known edge case 

- next step would be to use DB migration tool to initialize DB into known state;
  I often choose FlyWay
