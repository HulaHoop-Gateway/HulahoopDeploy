# Stage 1: Build
FROM gradle:7.6-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Stage 2: Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
