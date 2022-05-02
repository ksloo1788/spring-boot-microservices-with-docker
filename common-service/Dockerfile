FROM openjdk:17.0.2-oraclelinux8
MAINTAINER JeffLKS
COPY target/common-service-1.0.0.jar /bin/common-service.jar
ARG PORT_NUMBER
EXPOSE $PORT_NUMBER
ENTRYPOINT ["java","-jar","/bin/common-service.jar"]