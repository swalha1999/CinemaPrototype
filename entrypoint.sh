#!/bin/sh

# Start MySQL in the background
mysqld --user=mysql --datadir=/var/lib/mysql &

# Wait for MySQL to be fully up and running
until mysqladmin ping -h 127.0.0.1 --silent; do
  echo "Waiting for MySQL to start..."
  sleep 10
done

# Start the Java application
java -jar /app/application.jar
