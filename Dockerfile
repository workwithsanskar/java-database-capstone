# Stage 1: Build the application using Maven
FROM maven:3.8.1-openjdk-17 AS build

# Set working directory
WORKDIR /app

# Copy source code
COPY . .

# Build the application (skip tests)
RUN mvn clean package -DskipTests

# Stage 2: Run the application using JRE
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]