FROM openjdk:17-alpine
MAINTAINER JeffLKS
RUN mkdir -p /service/binary
COPY target/service-registry-server-1.0.0.jar /binary/service-registry-server.jar
EXPOSE 58010
ENTRYPOINT ["java","-jar","/binary/service-registry-server.jar"]