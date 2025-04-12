# Stage 1: Build the application
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy the Maven wrapper and project files
COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src

# Build the application
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Use a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expose the application port
EXPOSE 8080

# Define the entry point
ENTRYPOINT ["java", "-jar", "app.jar"]

