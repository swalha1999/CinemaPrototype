# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Install MySQL 8
RUN apk update && apk add mysql mysql-client && \
    mkdir /run/mysqld && \
    chown -R mysql:mysql /run/mysqld && \
    mysql_install_db --user=mysql --datadir=/var/lib/mysql

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=password
ENV MYSQL_DATABASE=MyFirstDatabase
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=password

# Copy the JAR file into the container
COPY out/artifacts/CinemaServer_jar/server.jar /app/application.jar

# Expose ports for MySQL and the application
EXPOSE 3306 8080

# Create an entrypoint script to start MySQL and the Java application
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

# Set the entrypoint
ENTRYPOINT ["/entrypoint.sh"]
