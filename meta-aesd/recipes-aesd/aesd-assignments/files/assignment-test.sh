#!/bin/bash

echo "Running Assignment 7 tests..."

# Load modules
echo "Loading scull..."
modprobe scull || { echo "Failed to load scull"; exit 1; }

echo "Loading faulty..."
modprobe faulty || { echo "Failed to load faulty"; exit 1; }

# Run scull tests
if [ -f /usr/bin/scull_load ]; then
    echo "Running scull tests..."
    /usr/bin/scull_load || { echo "scull_load failed"; exit 1; }
fi

# Run faulty driver test
if [ -f /usr/bin/faulty_load ]; then
    echo "Running faulty tests..."
    /usr/bin/faulty_load || { echo "faulty_load failed"; exit 1; }
fi

# Unload modules
echo "Unloading modules..."
rmmod faulty 2>/dev/null
rmmod scull 2>/dev/null

echo "Assignment 7 tests complete."
exit 0
