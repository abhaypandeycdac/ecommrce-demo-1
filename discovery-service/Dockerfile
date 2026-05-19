# Stage 1: Build Application
FROM maven:3.9.4-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy pom first for dependency caching
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Give execute permission to mvnw
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src ./src

CMD ["./mvnw", "spring-boot:run"]