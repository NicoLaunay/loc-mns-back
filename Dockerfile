FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /locnes
COPY --from=build /workspace/target/*.jar locnes.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "locnes.jar"]