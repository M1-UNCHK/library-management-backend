FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

ARG PROFILE=prod

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -P${PROFILE}

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/library-management-api.jar library-management-api.jar
EXPOSE 9090
CMD ["java", "-jar", "library-management-api.jar"]
