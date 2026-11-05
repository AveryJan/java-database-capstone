FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /project
COPY pom.xml .
COPY app ./app
RUN mvn -B clean package -DskipTests
FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd -r -u 10001 clinic
COPY --from=build /project/target/smart-clinic-1.0.0.jar app.jar
USER clinic
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
