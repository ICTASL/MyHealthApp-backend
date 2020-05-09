FROM maven:3.5.2-jdk-8-alpine AS build
COPY assembly.xml /tmp/
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn clean install
RUN unzip /tmp/target/covid19-1.0.0-SNAPSHOT.zip -d /tmp/target/
RUN ls -l /tmp/target

FROM frolvlad/alpine-java:jre8-slim
EXPOSE 8000
COPY --from=build /tmp/target/covid19-1.0.0-SNAPSHOT/*.* ./
ENTRYPOINT ["java","-jar","covid19.jar"]
