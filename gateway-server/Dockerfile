FROM openjdk:17.0.2-oraclelinux8
MAINTAINER JeffLKS
COPY target/gateway-server-1.0.0.jar /bin/gateway-server.jar
COPY keystore.p12 /bin/keystore.p12
EXPOSE 58000
ENTRYPOINT ["java","-jar","/bin/gateway-server.jar"]