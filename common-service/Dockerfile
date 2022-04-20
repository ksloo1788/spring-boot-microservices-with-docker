FROM openjdk:17-alpine
MAINTAINER JeffLKS
RUN mkdir -p /service/binary
COPY target/common-service-1.0.0.jar /binary/common-service.jar
ARG PORT_NUMBER
EXPOSE $PORT_NUMBER
ENTRYPOINT ["java","-jar","/binary/common-service.jar"]