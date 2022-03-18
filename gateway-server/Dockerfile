FROM openjdk:17-alpine
MAINTAINER JeffLKS
RUN mkdir -p /service/binary
COPY target/gateway-server-1.0.0.jar /binary/gateway-server.jar
COPY keystore.p12 /binary/keystore.p12
EXPOSE 58000
ENTRYPOINT ["java","-jar","/binary/gateway-server.jar"]