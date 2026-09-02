# Use Java 21 base image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built JAR
COPY --from=build /app/target/food_adda-0.0.1-SNAPSHOT.jar app.jar

# Expose port
EXPOSE 8080

# Copy application JAR
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