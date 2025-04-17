FROM openjdk:17-alpine
ARG JAR_FILE=target/weather-service-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} weather-service.jar
ENTRYPOINT ["java","-jar","/weather-service.jar"]