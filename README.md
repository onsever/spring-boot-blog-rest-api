# Blog Application REST API
This is a blog application that allows users to create, read, update, and delete blog posts. It also allows users to create, read, update, and delete comments on blog posts.

## Project Features
* Java
* Maven
* MySQL
* Spring Boot
* Spring Data JPA
* Spring Web
* Spring Security
* Lombok
* ModelMapper
* JWT (JSON Web Token)
* Hibernate Validator

## Project Architecture
This application uses the Three-Tier Architecture. The presentation layer, business layer, and data layer are separated from each other. The presentation layer is the user interface. The business layer is the business logic. The data layer is the database.

## Project Structure (Packaging Structure)
* **controller:** Contains the Spring MVC controller classes for the application.
* **entity:** Contains the JPA entity classes.
* **service:** Contains the service interfaces.
* **service.impl:** Contains the service implementation classes.
* **util:** Contains the utility classes.
* **repository:** Contains the JPA repository interfaces.
* **exception:** Contains the custom exception classes.
* **payload:** Contains the request and response payload classes.


```
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── com
│   │   │   │   ├── onurcansever
│   │   │   │   │   ├── blog
│   │   │   │   │   │   ├── controller
│   │   │   │   │   │   ├── entity
│   │   │   │   │   │   ├── exception
│   │   │   │   │   │   ├── repository
│   │   │   │   │   │   ├── service
│   │   │   │   │   │   ├── service.impl
│   │   │   │   │   │   ├── utils
│   │   │   │   ├── BlogRestApiApplication.java

```

## HTTP Request Flow
![diagram](https://github.com/onsever/spring-boot-blog-rest-api/blob/main/Flow%20Diagram.png)
