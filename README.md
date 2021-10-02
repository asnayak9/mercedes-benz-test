# mercedes-benz-test
The assignment to send a message from service 1 to service 2 without using http protocol.
Read using from service 2 using http protocol.

### Build tool
Apache Maven

### Language
- Java 1.8

### Frameworks
- Spring Boot
- Spring web
- ActiveMQ
- Jackson

### Encryption service
- RSA algorithm
- Keypair path: mb-common/src/main/resources/keypair

### Maven modules
1. **mb-common** - The common model and encryption classes to reuse
2. **service-sender** - Handles the HTTP request to store/update/read
3. **service-receiver** - Listens store/update request from queue, and one http handler method to read the file.

#### Service-1 API's <br />
**Run** _com.mercedes.benz.assignment.ServiceSenderApplication.java_
1. Store API - http://localhost:8888/v1/data/store
![image](https://user-images.githubusercontent.com/24394694/135723264-5bf6f4fb-8ca7-4a46-9409-70ff15e3179b.png)

2. Read API - http://localhost:8888/v1/data/read
![image](https://user-images.githubusercontent.com/24394694/135723311-4e7aca56-e897-4522-8613-5b94bfcf4dcc.png)

3. Update API - http://localhost:8888/v1/data/update
![image](https://user-images.githubusercontent.com/24394694/135723346-cac3b848-beea-4c32-bbd9-b4a554333847.png)
![image](https://user-images.githubusercontent.com/24394694/135723365-0a45baa3-4d9c-4c9f-a1fb-be9795b50515.png)


#### Service-2 API's <br />
**Run** _com.mercedes.benz.assignment.ServiceReceiverApplication.java_
1. Read API - http://localhost:8989/v1/data/read

#### ActiveMQ broker URL
tcp://localhost:61616

#### JMS Queues
1. message-store-queue
2. message-update-queue

