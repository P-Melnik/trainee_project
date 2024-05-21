FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/eureka_service.jar /app/eureka_service.jar

ENV SPRING_PROFILES_ACTIVE=docker

CMD ["java", "-jar", "eureka_service.jar"]