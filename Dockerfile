#Creates Docker Image
FROM maven:3-openjdk-17 AS build

# Copy everything from project into that image
COPY . .

# install application
RUN mvn clean package -DskipTests

# This is container I am going to run application in
FROM openjdk:17-ea-18-jdk-slim

COPY --from=build /target/phonepepi-0.0.1-SNAPSHOT.jar phonepepi.jar
EXPOSE 8080
# what command i am going to run
ENTRYPOINT ["java","-jar","phonepepi.jar"]


