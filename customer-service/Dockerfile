FROM maven:3.8.6-openjdk-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk

WORKDIR /app

COPY --from=build /app/target/customer-service.jar /app/customer-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/customer-service.jar"]