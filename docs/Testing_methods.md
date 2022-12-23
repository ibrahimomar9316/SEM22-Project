# Testing

Most of the code in the project is unit-tested, with mocks where needed.
Some intergration tests are also implemented when a class has to communicate with a database. This database is cleaned after every test of the class and only exists in memory for the lifetime of the testClass. 
For testing the overall functionality of the project, we used postman and the online interface of the databases. For the API endpoints we sent httpRequests to the microservices and checked if the response is indeed correct for the endpoint that was called. If an endpoint added something to a database, then we could check with the online interface if something was actually stored in the database. It was also possible to check with API endpoint that retrieved specific or all data from the database so we could compare everything.

