#!/bin/sh

echo "Checking port 3000..."
if nc -zv localhost 3000; then
  echo "Port 3000 is open"
  exit 0
else
  echo "Port 3000 is not open"
  exit 1
fi