# Oh My Bank API (OMB API)

## Building & Running the project

### Build the OMB API project
`./mvnw clean package`

### Run the OMB API tests
`./mvnw clean test`

### Run the OMB API
`./mvnw spring-boot:run`


### Build the OMB API Docker image
`docker build -t omb-api .`

### Run the OMB API docker image:
`docker run -p 8080:8080`


## Database

Insert object into mongo collection
```
{
   "_id": ObjectID(),
   "name": "Account 1",
   "routingNumber": "123", 
   "accountNumber": "450", 
   "openDate": "2021-11-22",
   "currentBalance": 0
}
```

## Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.6/maven-plugin/reference/html/#build-image)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#boot-features-mongodb)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.6/reference/htmlsingle/#boot-features-developing-web-applications)

## Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)

