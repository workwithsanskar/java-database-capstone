FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml backend/pom.xml
COPY backend/src backend/src
WORKDIR /app/backend
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /opt/smart-clinic
COPY --from=build /app/backend/target/smart-clinic-backend-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
