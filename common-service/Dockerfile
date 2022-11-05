FROM oraclelinux:9
RUN yum update -y; yum clean all
RUN yum install java-17-openjdk -y
MAINTAINER JeffLKS
COPY target/common-service-1.0.0.jar /bin/common-service.jar
ARG PORT_NUMBER
EXPOSE $PORT_NUMBER
ENTRYPOINT ["java","-jar","/bin/common-service.jar"]