FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/summary_service.jar /app/summary_service.jar

ENV SPRING_PROFILES_ACTIVE=docker

CMD ["java", "-jar", "summary_service.jar"]