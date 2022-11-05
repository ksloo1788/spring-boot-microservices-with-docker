FROM oraclelinux:9
RUN yum update -y; yum clean all
RUN yum install java-17-openjdk -y
MAINTAINER JeffLKS
COPY target/configuration-server-1.0.0.jar /bin/configuration-server.jar
EXPOSE 58000
ENTRYPOINT ["java","-jar","/bin/configuration-server.jar"]