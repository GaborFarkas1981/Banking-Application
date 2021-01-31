# Banking Application

Minimal [Spring Boot](http://projects.spring.io/spring-boot/) sample banking application.

## Requirements

For building and running the application you need:

JDK 11

Maven 3

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.gfarkas.banking.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
- First of all we need a Spring Boot starter app
https://start.spring.io/
- Adding some dependencies to the pom.xml (H2 embedded Database with Mysql, Lombok, QueryDsl, OpenApi (Swagger-ui))
- I want to use QueryDSL with Lombok and it won't work out of the box: https://stackoverflow.com/questions/44522494/how-to-make-querydsl-and-lombok-work-together


