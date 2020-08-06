# Animal eshop

Requirements: 
- JRE 14+
- MariaDB 11+ (optional)

## How to run

1) Using [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/):
    1) Build image with command `docker-compose build`.
    2) Deploy the stack with `docker-compose up -d`.

1) Manually:
    1) Compile with Maven using command `./mvnw clean package`.
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

- Domain Drive Development principles imply organizing class by their domain instead of class function
  (e.g. controller, repository, etc.) is preferred
  
- use of DTO decouples controllers and APIs from persistence layer, allowing for easier refactoring 
  or extraction of the features into micro-service in the future

- usage of service layer that contains business logic allows for easy extraction of some features
  into a micro-service in the future, as well as an elegant way of integrating already existing services
  - some teams like to add axtra layer of abstraction and implement each operation as a separate
    function class (`java.util.function`)

- controllers should not contain business logic, their responsibility is in validating inputs
  and presenting outputs

- usage of HTTP Basic authentication for purpose of demo
  - in real world app I would use oAuth2 or JWT based auth with checks for token revocation  

- I would argue against requiring username in addition to email
  - email already serves as unique identifier and is required
  - unless users interact with each other, protecting their email from each other is not an issue
  - requiring more data from the user and requiring them to think up a unique username, 
    when their desired one can be already be taken, creates more friction in 
    user onboarding process, leading to lower conversion rates

- use of `JpaRepository.getOne(id)` creates proxy instead of querying the DB, thus improving performance

- in this setup method parameters constraint violations are not being correctly serialized;
  if this was fixed many more tests would be possible
  
- I always try to create one test for intended use case, and one for each known edge case

- next step would be to use DB migration tool to initialize DB into known state;
  I often choose FlyWay

- build process can be optimized for prod usage by taking advantage of distro-less images
  and/or utilizing jit build system to separate dependencies layer from app code 

- prefer kotlin for new project due to its language level null-safety, since assignment was specifief
  to be implemented in Java I utilized JSR-305 maven plugin to provide some level of null-safety
  with compile time warnings 
