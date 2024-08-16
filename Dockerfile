# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/account-deletion-requests-0.0.1-SNAPSHOT.jar /app/account-deletion-requests-0.0.1-SNAPSHOT.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/account-deletion-requests-0.0.1-SNAPSHOT.jar"]

# Expose the port the app runs on
EXPOSE 8080
