# ====== Build Stage ======
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy all files
COPY . .

# Build the JAR
RUN mvn clean package -DskipTests

# ====== Run Stage ======
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy built jar from previous stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
