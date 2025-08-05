# ========================#
# INFO: Application Build
# ========================#
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# =====================#
# INFO: RUNTIME IMAGE
# =====================#
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar api.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]