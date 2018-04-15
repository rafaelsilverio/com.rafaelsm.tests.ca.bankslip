# How to compile and run the application 
At the root of the repository, execute the command
```
$ mvn spring-boot:run
```
Wait for the webserver to start, you'll be abble to access the application using the url: 
```
http://localhost:8080/
```

# How to run tests
At the root of the repository, execute the command
```
$ mvn test
```

# Available API Calls

All required endpoints were implemented:

```
POST http://localhost:8080/rest/bankslips
```
```
GET http://localhost:8080/rest/bankslips
```
```
GET http://localhost:8080/rest/bankslips/{id}
```
```
PUT http://localhost:8080/rest/bankslips/{id}/pay
```
```
DELETE http://localhost:8080/rest/bankslips/{id}/cancel
```

# To be improved

- Write more integration tests, due to time constraints I've only implemented one integration test (payBankSlipSuccessfully).
- Capture requests errors (such as incorrect attributes or invalid values) and return a standard ResponseMessage object.























