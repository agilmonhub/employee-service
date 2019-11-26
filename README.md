### Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

### Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.employee.EmployeeServiceApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run -Dspring.profiles.active="local"

test cases
mvn test

profile : local
port: 8081
base url: localhost:8081/
registration: localhost:8081/registration
List: localhost:8081/employee
```

### Swagger Information.

For running please refer the below url
```shell script
http://localhost:8081/swagger-ui.html
```

### Portal information

Functionality support
````
1. Register Employee.
2. Update Employee.
3. Delete Employee.
4. Get Employee by Id.
````

### Database used

In memory data base

````
H2 Database.

Data will be flushed when the server is restarted.
````
