FROM maven:3.9.9-eclipse-temurin-24-alpine	AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package

CMD ["java", "-jar", "target/license-plate-generator-1.0-SNAPSHOT.jar"]

