# Use an official OpenJDK runtime as a parent image
FROM openjdk:22

# Copy the JAR file into the container
COPY dockerOut/server.jar /app/application.jar

# Expose ports for the application
EXPOSE 3000

# Command to run the Java application
CMD ["java", "-jar", "/app/application.jar"]