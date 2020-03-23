FROM frolvlad/alpine-java:jre8-slim
EXPOSE 8000
COPY target/covid19-1.0.0-SNAPSHOT/*.* ./
ENTRYPOINT ["java","-jar","covid19.jar"]

