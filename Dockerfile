# Stage 1: Build with Maven
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run with JDK
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/food_adda-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


# # Use Java 21 base image
# FROM eclipse-temurin:21-jdk-alpine

# # Set working directory
# WORKDIR /app

# # Copy built JAR
# COPY target/food_adda-0.0.1-SNAPSHOT.jar app.jar

# # Expose port
# EXPOSE 8080

# # Run application
# ENTRYPOINT ["java", "-jar", "app.jar"]