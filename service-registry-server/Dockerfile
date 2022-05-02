FROM openjdk:17.0.2-oraclelinux8
MAINTAINER JeffLKS
COPY target/service-registry-server-1.0.0.jar /bin/service-registry-server.jar
EXPOSE 58010
ENTRYPOINT ["java","-jar","/bin/service-registry-server.jar"]