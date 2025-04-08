#!/bin/bash
# This script builds the project using the specified build tool

mkdir -p class
find src -name "*.java" -print | xargs javac -d class

java -cp class src.Main