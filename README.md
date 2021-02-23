# About the application

This is API is allowing users to add employees accounts and update
their working state. 

# Used technologies:
-Spring boot framework.
-Java 11.
-H2 in Memory database.
-Apache kafka.

# used tools:
-Intellij.
-Postman as a client for the API.
-Docker.
 
# Steps to use docker to run this APi:
1-Building the API image:
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;On the directory of the Dockefile run the follwing command:
   
- docker build -f Dockerfile -t challenge .

 2-Containrizing and running KAFKA, Zookeeper and the API
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Run the follwing command respectivly
   
- docker network create kafkanetlink
- docker run --network=kafkanetlink --rm --detach --name zookeeper -e ZOOKEEPER_CLIENT_PORT=2181 confluentinc/cp-zookeeper:5.5.0
- docker run --network=kafkanetlink --rm --detach --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=1 -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka:9092 -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 confluentinc/cp-kafka:5.5.0
- docker run --network=kafkanetlink --rm --name challenge -p 8083:8083 --tty challenge kafka:9092

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;It is recommended now to use postman or any other client to send requsts and receive responses.

# How to run testing :
   The provided testing cLass could run on any IDE.

# OpenAPI 
 after running the API, openAPI definition could be exposed using the link below:
http://localhost:8083/swagger-ui.html




# REST API Endpoints:
This REST API having two different endpoints:
1-Add new employee.
2-Update employee working state. 


## Add new employee: 

### Request body parameters and specifications:
 URI:localhost:8083/employees
    Method: Post

    {
      "name":"{not empty or blank}",
      "email":"{not empty or blank and valid unique mail}",
      "age":"{2 digit num}",
      "phone":"{valid Egyptian 11 digit phone num}",
      "contractInfo":"{string}",
    }

### Request and respone Example for a specific employee:
    
     {
     "name":"Passant",
     "email":"passant@gmail.com",
     "age":"27",
      "phone":"01234567890",
     "contractInfo":"permanent",
     }

### Response:
    HTTP status: 201 Created
    
    {
    "name": "Passant",
    "age": 27,
    "adress": null,
    "contractInfo": "permanent",
    "email": "passant@gmail.com",
    "phone": "01234567890",
    "state": "ADDED"
    }
      

### Invalid requests and their responses:

Request: any request with any missing attribute or any attribute with invalid value.
Response: response body contains time, error message and details about the
invalid field.

     HTTP status: 400 bad request.
     
     {
    "timestamp": "2021-02-22T15:01:57.564+0000",
    "message": "Validation Failed",
    "details": "org.springframework.validation.BeanPropertyBindingResult: 1 errors\nField error in object 'addEmployeeReqDto' on field 'name': rejected value []; codes [NotBlank.addEmployeeReqDto.name,NotBlank.name,NotBlank.java.lang.String,NotBlank]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [addEmployeeReqDto.name,name]; arguments []; default message [name]]; default message [please specify employee name]"
    }

Request: request with duplicate email.
Response :

     HTTP status: 409 conflict.
     
    {
    "timestamp":"2021-02-22T10:27:57.500+0000",
    "message":"email already exists",
    "details":"uri=/employees;client=172.25.0.1"
    }
    
## Update employee state: 

### Request body parameters and specifications:
URI:localhost:8083/employees
    Method: Put

    {
    "id":"{not empty or blank and Existed employee id}"",
    "state":"{not empty or blank and has one of these value ("ADDED","IN-CHECK","APPROVED","ACTIVE")}
    }

### Request and respone Example for a specific user:
    
    {
    "id":"1",
    "state":"CHECK"
    }

### Response 
    HTTP status: 200 ok
    
### Invalid requests and their response:

Request: any request with any missing attribute or any attribute with invalid value.
Response: response body contains time, error message and details about the
invalid field.

    HTTP status: 400 bad request.
      
    {
    "timestamp": "2021-02-22T14:53:29.429+0000",
    "message": "Validation Failed",
    "details": "JSON parse error: Cannot deserialize value of type `com.employees.challenge.model.Employee$State` from String \"ADDE\": not one
     of the values accepted for Enum class: [ADDED, INCHECK, ACTIVE, APPROVED]....
     ......"
    }

Request: request with inexistent id 
Response:

      HTTP status: 404 Not found.
       
    {
    "timestamp":"2021-02-22T10:29:40.786+0000",
     "status":404,
     "error":"Not Found",
     "message":"Employee not found for this id: 2",
     "path":"/employees"
     }

    

