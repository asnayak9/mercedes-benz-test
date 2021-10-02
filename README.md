# mercedes-benz-test
The assignment to send a message from service 1 to service 2 without using http protocol.
Read using from service 2 using http protocol.

### Build tool
Apache Maven

### Language
Java 1.8

### Frameworks
1. Spring Boot
2. Spring web
3. ActiveMQ
4. Jackson

### Encryption service
RSA algorithm

### Maven modules
1. **mb-common** - The common model and encryption classes to reuse
2. **service-sender** - Handles the HTTP request to store/update/read
3. **service-receiver** - Listens store/update request from queue, and one http handler method to read the file.

#### Service-1 API's
1. Store API - http://localhost:8888/v1/data/store
2. Read API - http://localhost:8888/v1/data/read
3. Update API - http://localhost:8888/v1/data/update

#### Service-2 API's
1. Read API - http://localhost:8888/v1/data/read
