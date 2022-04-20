# Microservices Architecture With Spring Boot, Spring Cloud and Docker

This project demonstrates how to create a microservices architecture with Spring Boot and Spring Cloud. 
It also shows how to package each application in Docker containers and run them with Docker Compose.

**Prerequisites:** 

* [Java JDK 17](https://openjdk.java.net/projects/jdk/17/)

* [Eclipse](https://www.eclipse.org/)

* [Spring Tools for Eclipse IDE](https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4/)

* [Apache Maven](https://maven.apache.org/)

* [Docker Desktop](https://www.docker.com/)

## Content
* [Getting Started](#getting-started)
* [Help](#help)
* [Links](#links)
* [License](#license)


## Getting Started
To install this example application, run the following commands:

```bash
git clone https://github.com/ksloo1788/spring-boot-microservices-with-docker.git
cd spring-boot-microservices-with-docker
```

Run the following command from the root folder to create Docker containers to build Java libraries for all Spring Boot Applications.

```shell
./mvnw clean install
```

Then you can start your microservices architecture using Docker Compose:

```shell
docker-compose up --force-recreate --build -d && docker image prune -f
```

Last but not least, you can remove all dangling images:

```shell
docker image prune -f
```

**TIP:** 
After everything starts, you should be able to check Eureka Service Registry at `http://localhost:58010`.

## Links

This project uses the following open source projects:
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Cloud](https://spring.io/projects/spring-cloud)
* [Spring Cloud Netflix Eureka](https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html)
* [Spring Cloud Config](https://cloud.spring.io/spring-cloud-config/multi/multi__quick_start.html)
* [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

## Help

Please post any questions as comments on this repository or [email us](mailto:ks_loo@outlook.com). 

## License

GNU General Public License v3.0, see [LICENSE](https://www.gnu.org/licenses/gpl-3.0.en.html).
