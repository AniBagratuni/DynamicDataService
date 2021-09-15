# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.4/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.4/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/2.5.4/reference/htmlsingle/#boot-features-jpa-and-spring-data)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

DynamicDataService application is a basic Spring boot REST service.

Swagger available with this path: http://localhost:8080/swagger-ui-custom.html

DynamicDataService.postman_collection.json postman collection provided for test category creation and category data CRUD operations

Task Technical details:

Create CRUD API for DynamicDataService

- POST /api/v1/category create a new category

- POST    /api/v1/categoryData - creating a new data for category
- GET     /api/v1/categoryData/{categoryName}/{id} - getting a data for given category
- DELETE  /api/v1/categoryData//{categoryName}/{id}- delete data of category
- PUT     /api/v1/categoryData- update data of category


Response statuses:

- 200 (OK) - the request was successful (for all successful responses except for the response to add category and category data)
- 201 (CREATED) - category and category data added successfully
- 400 (BAD_REQUEST) - Invalid request format or field values

Need to run the application, add category, then restart the application. after that we can use CRUD actions for category data

