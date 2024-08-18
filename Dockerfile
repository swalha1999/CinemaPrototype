# Use an official OpenJDK runtime as a parent image
FROM openjdk:22

# Copy the JAR file into the container
COPY dockerOut/server.jar /app/application.jar

# Expose ports for MySQL and the application
EXPOSE 3306 8080 3000

# Create an entrypoint script to start MySQL and the Java application
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]
