#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11.0-slim
COPY --from=build /home/app/target/*-SNAPSHOT.war app.war
ENTRYPOINT ["java","-jar","/app.war"]
EXPOSE 5000