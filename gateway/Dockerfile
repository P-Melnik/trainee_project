FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/gateway_service.jar /app/gateway_service.jar

ENV SPRING_PROFILES_ACTIVE=docker

CMD ["java", "-jar", "gateway_service.jar"]