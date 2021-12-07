FROM maven:3-jdk-11 as builder

COPY src /app/src
COPY pom.xml /app
WORKDIR /app
RUN mvn package

FROM openjdk:11-jre-slim-buster
COPY --from=builder /app/target /app/target
ENTRYPOINT ["java","-jar","/app/target/omb-api-0.0.1-SNAPSHOT.jar"]