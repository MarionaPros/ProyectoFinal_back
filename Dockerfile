FROM maven:3-eclipse-temurin-21-alpine AS build
COPY src /tmp/src
COPY pom.xml /tmp
RUN mvn -f /tmp/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
COPY --from=build /tmp/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
