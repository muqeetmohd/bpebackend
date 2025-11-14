# ====== Build Stage ======
FROM gradle:8.3-jdk17 AS build
WORKDIR /app

# Copy Gradle wrapper and build files first (for caching)
COPY gradle ./gradle
COPY gradlew .
COPY build.gradle .
COPY settings.gradle .

# Copy source code
COPY src ./src

# Build the JAR
RUN ./gradlew build -x test

# ====== Run Stage ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
