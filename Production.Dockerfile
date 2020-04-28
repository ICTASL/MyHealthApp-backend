FROM maven:3.5.2-jdk-8-alpine AS build
COPY assembly /tmp/assembly
COPY backend /tmp/backend
COPY portal-frontend /tmp/portal-frontend
COPY pom.xml /tmp/
WORKDIR /tmp/
RUN mvn clean install
RUN unzip /tmp/assembly/target/myhealth-server-1.0.0.zip -d /tmp/target/
RUN ls -l /tmp/target

FROM frolvlad/alpine-java:jre8-slim
EXPOSE 8000
COPY --from=build /tmp/target/myhealth-server-1.0.0/*.* ./
ENTRYPOINT ["java","-jar","myhealth-server.jar"]
