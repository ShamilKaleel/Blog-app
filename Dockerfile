# Use Maven image to build the project
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copy source code
COPY . .

# Build the application

# Use OpenJDK runtime to run the built JAR
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
