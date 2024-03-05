FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/gymapp.jar /app/gymapp.jar

EXPOSE 8080

CMD ["java", "-jar", "gymapp.jar"]
