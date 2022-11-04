FROM maven:3.8.3-openjdk-17-slim as builder
RUN addgroup sumting; adduser  --ingroup sumting --disabled-password sumyting
USER sumyting:sumting
#WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY ./src ./src
CMD ["./mvnw", "spring-boot:run"]
#
#FROM eclipse-temurin:17-jre-jammy
#WORKDIR /app
#EXPOSE 8080
#COPY --from=builder /app/target/*.jar /app/*.jar
#ENTRYPOINT ["java", "-jar", "/app/*.jar" ]