FROM oraclelinux:9
RUN yum update -y; yum clean all
RUN yum install java-17-openjdk -y
MAINTAINER JeffLKS
COPY target/gateway-server-1.0.0.jar /bin/gateway-server.jar
COPY keystore.p12 /bin/keystore.p12
EXPOSE 58000
ENTRYPOINT ["java","-jar","/bin/gateway-server.jar"]