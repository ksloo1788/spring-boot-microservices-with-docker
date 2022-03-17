FROM openjdk:17-alpine
MAINTAINER JeffLKS
RUN mkdir -p /service/binary
COPY target/configuration-server-1.0.0.jar /service/binary/configuration-server.jar
EXPOSE 58000
ENTRYPOINT ["java","-jar","/service/binary/configuration-server.jar"]