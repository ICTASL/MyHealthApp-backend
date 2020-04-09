FROM frolvlad/alpine-java:jre8-slim
EXPOSE 8000
COPY target/covid19-1.0.0-SNAPSHOT/*.* ./
RUN rm /etc/localtime
RUN ln -s /usr/share/zoneinfo/Asia/Colombo /etc/localtime
ENTRYPOINT ["java","-jar","covid19.jar"]

