#FROM maven:3-openjdk-18 as builder
FROM maven:3-openjdk-18  as builderJDK
LABEL maintainer="info@pdik.nl"
VOLUME /tmp
WORKDIR /app
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY ./src ./src
#CMD ["./mvnw", "spring-boot:run"]

EXPOSE 8083
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","app.jar"]