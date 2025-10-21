# Stage 1: Build
FROM maven:3.9.2-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Render will provide $PORT automatically
EXPOSE ${PORT}

# Run the app
CMD ["java", "-jar", "app.jar"]
