## Overview

The ms-notification repo is a backend to generate notifications.

## Setup

### Java

Install java 8 or 11

### Database

We have used embedded h2 database. schema [Database Schema](src/main/resources/docs/schema.sql)

### Kafka

Install docker on a machine and execute [Docker Compose file](docker-compose.yaml) with following command to install
kafka and run zookeper both by execute the following  command: 
###docker-compose up -d 

## API Request payload

1. Create notification by user url: localhost:8080//api/notification/create
   payload: [Request Payload](src/main/resources/docs/request_payload.json)
2. Publish notification on a kafka url: localhost:8080//api/notification/publish
   payload: [Request Payload](src/main/resources/docs/request_payload.json)
3. Postman collection [Postman Collection](src/main/resources/docs/Notification%20System.postman_collection.json)

## Build and start the application with following commands

1. ./gradlew clean build
2. ./gradlew bootRun

First command perform the following tasks

1. Clean the build dir
2. compile the code
3. Executes the test cases like unit test cases, integration test cases etc.
4. Create a new jar file in build/libs dir

5. Second command start the application
