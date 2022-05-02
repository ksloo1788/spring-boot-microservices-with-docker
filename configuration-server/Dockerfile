FROM openjdk:17.0.2-oraclelinux8
MAINTAINER JeffLKS
COPY target/configuration-server-1.0.0.jar /bin/configuration-server.jar
EXPOSE 58000
ENTRYPOINT ["java","-jar","/bin/configuration-server.jar"]