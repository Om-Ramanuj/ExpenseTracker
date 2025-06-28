# Use an official Maven image to build the app
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies (for faster rebuilds)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Production image with JDK only (smaller size)
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy built JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port (optional for local Docker; Render sets this automatically)
EXPOSE 8080

# Use environment variable PORT if set, else default to 8080
ENV PORT=8080

# Run the Spring Boot application
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]
