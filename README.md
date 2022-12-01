# NinjaOne Backend Interview Project

This project contains [Instructions](INSTRUCTIONS.md) that must be read in order to perform NinjaOne's code assessment.
Also the project is configured to use an in-memory H2 database that is volatile. If you wish to make it maintain data on
application shut down, you can change the spring.database.jdbc-url to point at a file like `jdbc:h2:file:/{your file path here}`

## Prerequisites

This application was developed using the following technologies required for compilation and execution:

* Java 11
* Maven 3.8.1
* Spring Boot 2.7.5


## Build

To install run the following command inside the main folder of the project:

```sql
mvn clean install
````



To generate the JAR file, rum the following command:


```sql
mvn package
````
This above command will recompile the project and run all the unit tests.


To run the application, inside the main folder of the project, run the following command:

```sql
java -jar target/rmm-1.0.jar
````

The default port that this application is running is 9090, if you want to change the port, you must edit the file 'application.properties' located in the 'resources' folder locate in the following path '\rmm\src\main\resources\', however, if the port is changed , the urls of this document must be changed to follow the new configuration.


To stop the application via the command line, run the following command:

```sql
control + c
````



## Starting the Application

If the application is not running, we must run the `RmmApplication` class

To access details of each object, go to:

##### Type:
* [http://localhost:9090/rmm/types/1](http://localhost:9090/rmm/types/1)

##### Device:
* [http://localhost:9090/rmm/devices/1](http://localhost:9090/rmm/devices/1)

##### Service:
* [http://localhost:9090/rmm/services/1](http://localhost:9090/rmm/devices/1)

##### Price per Service:
* [http://localhost:9090/rmm/price-per-services/1](http://localhost:9090/rmm/price-per-services/1)

##### Operational System:
* [http://localhost:9090/rmm/ops/1](http://localhost:9090/rmm/ops/1)

##### Customer:
* [http://localhost:9090/rmm/customers/1](http://localhost:9090/rmm/customers/1)


You should see results for each of these requests.. The application is working and connected to the H2 database. 


## H2 Console 

In order to see and interact with your db, access the h2 console in your browser.
After running the application, go to:

[http://localhost:9090/h2-console](http://localhost:9090/h2-console)

Enter the information for the url, username, and password, if need, in the application.properties:

```yml
url: jdbc:h2:mem:rmmdb
username: sa 
password: 
```

You should be able to see a db console now that has access to all repositories in it. In order to access each respective list of each object, execute as follows:

##### Type:

```sql
SELECT * FROM TYPES;
````

Click `Run`, you should see three rows, for ids `1`, `2` and `3`


##### Device:

```sql
SELECT * FROM DEVICE;
````

Click `Run`, you should see three rows, for ids `1`, `2` and `3`


##### Service:

```sql
SELECT * FROM SERVICES;
````

Click `Run`, you should see five rows, for ids from `1` to `5`


##### Price per Service:

```sql
SELECT * FROM PRICE_PER_SERVICE;
````

Click `Run`, you should see ten rows, for ids from `1` to `10`. It is in this table where we insert the price of each respective service based on the respective operating system, since we may have different prices for each service.


##### Operational System:

```sql
SELECT * FROM OP_FAMILY;
````

Click `Run`, you should see three rows, for ids `1`, `2` and `3`


##### Customer:

```sql
SELECT * FROM CUSTOMERS;
````

Click `Run`, you should see three rows, for ids `1`, `2` and `3`


##### List all devices of a customer, execute the SQL command below:

```sql
SELECT * FROM CUSTOMERS_DEVICES;
````

Click `Run`


##### List all services of a customer, execute the SQL command below:

```sql
SELECT * FROM CUSTOMERS_SERVICES;
````

Click `Run`


A data preload will be done when running the application, to change the data, change the SQL scipts in the import.sql file.


## Swagger 

To access each documentation and test each API, Swagger was used to give more details of the classes and features available in our APIs. After running our application, you can access all the details through the link::

[http://localhost:9090/swagger-ui/index.html](http://localhost:9090/swagger-ui/index.html)

For access to swagger documentation, go to::

[http://localhost:9090/rmm/swagger-api-docs](http://localhost:9090/rmm/swagger-api-docs)



## Details 

The unit tests were created using two strategies, one mocking data and the other accessing data inserted in the database in H2 memory, this data was inserted via initialization script through the 'import.sql' file, these approaches were implemented in order to demonstrate the different possible implementations.



## Improvements

* Implementing token generation for restricted access to APIs using Spring Oauth or JWT.
* Remove the manual mapping implemented in the EntityMapper class and use a library such as ObjectMapper, as the manual implementation was done only for demonstrating knowledge.
* Increase unit test coverage
* Change the In-memory database to a more conventional database like MySQL, Oracle, for example



