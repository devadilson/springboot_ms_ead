# Spring Boot Project Microservices EAD
Restful API project in Spring Boot 3 and Java 17 of an EAD system using microservices

#
### NOTE: The Same Project with Broken Pattern and Authentication and Authorization with JWT Token, change for branch #MAINV2#

### Starting Microservices
#
1 - Service Registry
#
2 - API Gateway
#
3 - Config Server
#
4 - AuthUser Service
#
5 - Course Service
#
6 - Notification Service
#

## Following the order described above, navigate to the directory of each spring project and run the command below.
mvn spring-boot:run -Dspring-boot.run.arguments=--logging.level.com.ead=TRACE

#
### EAD Application Architecture
![EAD Architecture](https://github.com/devadilson/springboot_ms_ead/blob/main/ead_diagram/EAD-Arquitetura-Microservices-EAD-Architecture.drawio.png?raw=true)

#
### EAD Diagram Flux Subscription User in Courses
![EAD Flux Subscription User in Course](https://github.com/devadilson/springboot_ms_ead/blob/main/ead_diagram/Diagrama-Flux-Subscription-UserInCourse.drawio.png?raw=true)

#
### EAD Diagram Flux Delete Course
![EAD Diagram Flux Delete Course](https://github.com/devadilson/springboot_ms_ead/blob/main/ead_diagram/Flux-Delete-Course.drawio.png?raw=true)

#
### EAD Diagram Flux Delete User
![EAD Diagram Flux Delete User](https://github.com/devadilson/springboot_ms_ead/blob/main/ead_diagram/Flux-Delete-User.drawio.png?raw=true)

#
### EAD Eureka Client and LoadBalance with API Gateway e Service Discovery Pattern in Microservices
![EAD Eureka Client and LoadBalance with API Gateway e Service Discovery Pattern in Microservices](https://github.com/devadilson/springboot_ms_ead/blob/main/ead_diagram/Eureka_Client_LoadBalance_MS.png?raw=true)
