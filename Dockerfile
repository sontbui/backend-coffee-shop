# docker build -t back-end-app . -> need to run this command in the same directory as the Dockerfile
# Use an official Maven image with OpenJDK as a parent image
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml .

# Copy the source code to the container
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image for the final image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/back-end-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the application will run on
EXPOSE 8088

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]

HEALTHCHECK --interval=30s --timeout=10s --start-period=10s \
  CMD curl -f http://localhost:8088/actuator/health || exit 1
