FROM oraclelinux:9
RUN yum update -y; yum clean all
RUN yum install java-17-openjdk -y
MAINTAINER JeffLKS
COPY target/service-registry-server-1.0.0.jar /bin/service-registry-server.jar
EXPOSE 58010
ENTRYPOINT ["java","-jar","/bin/service-registry-server.jar"]