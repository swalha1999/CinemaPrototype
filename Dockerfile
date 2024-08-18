# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim

# Install netcat-openbsd using apt-get
#RUN apt-get update && apt-get install -y netcat-openbsd && rm -rf /var/lib/apt/lists/*

# Copy the JAR file into the container
COPY dockerOut/server.jar /app/application.jar
#COPY healthCheck.sh /app/healthCheck.sh
#
#RUN chmod +x /app/healthCheck.sh

# Expose ports for the application
EXPOSE 3000

# Command to run the Java application
CMD ["java", "-jar", "/app/application.jar"]

# Health check: Try to connect to the server on port 3000
HEALTHCHECK CMD netstat -an | grep 3000 > /dev/null; if [ 0 != $? ]; then exit 1; fi;

