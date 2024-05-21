FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN apk update && apk add postgresql-client

COPY target/gymapp.jar /app/gymapp.jar
COPY src/main/resources/import.sql /app/import.sql
COPY entrypoint.sh /app/entrypoint.sh

RUN chmod +x /app/entrypoint.sh

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["/app/entrypoint.sh"]