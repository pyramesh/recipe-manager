## Objective

Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.
   For example, the API should be able to handle the following search requests:
   • All vegetarian recipes
   • Recipes that can serve 4 persons and have “potatoes” as an ingredient
   • Recipes without “salmon” as an ingredient that has “oven” in the instructions.

##    Requirements

Please ensure that we have some documentation about the architectural choices and also how to
run the application. The project is expected to be delivered as a GitHub (or any other public git
hosting) repository URL.

All these requirements needs to be satisfied:

1. It must be a REST application implemented using Java (use a framework of your choice)
2. Your code should be production-ready.
3. REST API must be documented
4. Data must be persisted in a database
5. Unit tests must be present
6. Integration tests must be present

----------------------------------------- 

## Application guide

   ## Technologies used to implement the business use case

   1. Java 8
   2. Spring Boot 2.7.8 - REST API, Transacton management
   3. Spring Data JPA
   4. Lombok
   5. Map Struct  - Map one object to other object. lot of boilerplate can be reduced.
   6. H2 DB       -In-memory database to persist the data
   7. Maven       - Build the project
   8. OpenAPI     - REST API documentation
   9. CQRS         - Deisgn pattern used for REST API implementation.


   ## How to build and run the application

   1. Prerequisite: Java 8, and Maven should be available to build the application.
   2. Open command prompt in the root folder of the project where POM.xml exists.
   3. Execute the below commands. this will build the project and executes all unit tests and integration test cases. 
      ```
      $ mvn clean install
      ``` 
      4. To run the test cases.
       ```
      $ mvn test
        ```   
   5. Once build is success, Jar (recipe-manager-0.0.1-SNAPSHOT)will be generated under the /target folder 
           (Example: recipe-manager/target/recipe-manager-0.0.1-SNAPSHOT) 
   6. Open command prompt in target folder and execute the below command to run the application.
    
      ```
      $ java -jar recipe-manager-0.0.1-SNAPSHOT
      ``` 
   6. And then logs shows that the application is started successfully like 'Tomcat started on port(s): 8080 (http) with context path'
   7. Open the below link to access REST API documentation.
   
       http://localhost:8080/swagger-ui/index.html

## Implementation details and assumptions

1. Assumption : Since Many Recipes can contains many ingredients and many ingredients can be used for many recipes.
   So I decided to use Many to Many JPA mapping between Recipe and Ingredient
2. CQRS deisgn pattern followed for REST API implementation.
3. H2 in-memory database being used to persist the data. once the application is up and running, h2 console can be accessed 
   using the below link (http://localhost:8080/h2-console)
4. Implemented exception handling (ControllerAdvice), logging (Sl4j) and transaction management.
5. Total 79 test cases have been implemented. 
6. And the code coverage is around 80%.
7. I tried implemented as much production ready as I could, But few things still could have been improved like bean
   validations and testcases. 



