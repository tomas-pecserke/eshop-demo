FROM openjdk:14-alpine

RUN mkdir /app/
WORKDIR /app
ADD src/ src/
ADD pom.xml pom.xml
ADD .mvn/ .mvn/
ADD mvnw mvnw
RUN ./mvnw -B -DskipTests=true clean package \
    && mv target/animal-eshop-*.jar target/animal-eshop.jar

FROM openjdk:14-alpine

RUN mkdir /app/
COPY --from=0 /app/target/animal-eshop.jar /app/animal-eshop.jar
WORKDIR /app/
CMD ["/opt/openjdk-14/bin/java", "-jar", "animal-eshop.jar"]
EXPOSE 8080
