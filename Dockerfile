# Use an official OpenJDK runtime as a parent image
FROM openjdk:22

# Copy the JAR file into the container
COPY dockerOut/server.jar /app/application.jar

# Expose ports for the application
EXPOSE 3306 8080 3000

# Add a health check to monitor the application
#HEALTHCHECK --interval=30s --timeout=5s --retries=3 \
#  CMD curl --fail http://localhost:8080/health || exit 1

# Command to run the Java application
CMD ["java", "-jar", "/app/application.jar"]