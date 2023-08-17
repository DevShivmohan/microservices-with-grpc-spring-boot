# grpc-microservice-spring-boot - Activity 1

**In grpc-microservices we have 2 microservices**
- grpc-service.
- client-service.
- We have used as a model Book in proto file.


**In these microservices i'm trying to integrate build 3 things**
- Synchronous call with single request and single response.
- Asynchronous call with single request and stream of response.
- Asynchronous call with stream of request and single response.

# Create openssl certificate for localhost

//Generate CA's private key <br>
openssl genrsa -des3 -out ca.key.pem 2048 

//create CA's self-signed certificate<br>
openssl req -x509 -new -nodes -key ca.key.pem -sha256 -days 365 -out localhost.cert.pem

//create private key for server<br>
openssl genrsa -out localhost.key 2048

//create certificate signing request (CSR)<br>
openssl req -new -key localhost.key -out localhost.csr

//Use CA's private key to sign web server's CSR and get back the signed certificate<br>
openssl x509 -req -in localhost.csr -CA localhost.cert.pem -CAkey ca.key.pem -CAcreateserial -out localhost.crt -days 365

//convert server private key in PKCS8 standard(gRPc expects)<br>
openssl pkcs8 -topk8 -nocrypt -in localhost.key -out localhost.pem

### Demo with live screenshots

![image](https://github.com/DevShivmohan/microservices-with-grpc-spring-boot/assets/72655528/4220d438-3295-4838-9336-7b28feef4027)
![image](https://github.com/DevShivmohan/microservices-with-grpc-spring-boot/assets/72655528/43825d6f-8d2b-4af6-891c-2015715e488d)


# Apache kafka with consumer and producer model - Activity 2

- For installation and configurations please [click here](https://github.com/DevShivmohan/Learning-everything/issues/36#issuecomment-1680416910)
