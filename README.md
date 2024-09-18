# OnlineShop

OnlineShop is a project made with cutting-edge tools, which allows users to access a website, log in with their username, place orders of products and purchase them.

This is done in a microservices architecture, using in all of them the hexagonal architecture, with a DDD and Api-First approach. Below, I leave a list of all the technologies used and implemented on this project, and also another one with the technologies that are yet to be implemented.

### Backend technologies (Java & Spring Boot)

* Languages: Java 21
* Frameworks: Spring Boot, Spring JPA
* Databases: PostgreSQL, MongoDB
* Messaging: Kafka & ZooKeeper
* Testing: Junit, Mockito
* API: OpenAPI
* Others: Maven, Lombok

### Frontend technologies (Angular)

* Languages: TypeScript, HTML, CSS
* Framework: Angular
* Libraries: Angular Material, RxJS, NgRx
* Tools: Angular CLI, npm, Webpack, TypeScript Compiler
* Others: Node.js

### To be implemented

* Spring Security
* Jenkins
* SonarQube
* Docker
* Kubernetes

# Local Installation Steps

## Java & Maven Installation
First of all, you have to install Java 21 following [this link](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html).
Then, install Maven:
- For Windows: you must download the [Maven Binary Zip](https://maven.apache.org/download.cgi) file. Then, you must extract it in the directory where you 
want to have it saved, and copy the path that leads to the 'bin' folder of the newly downloaded directory. Once this is done, you should go to Windows Search -> Environment Variables -> User Variables -> Path -> Edit -> New -> and finally paste the previously copied path.

- For Linux: you will need to do the following (change the version to the newest version [at this link](https://maven.apache.org/download.cgi)):
```
$ wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
$ tar -xvf apache-maven-3.6.3-bin.tar.gz
$ mv apache-maven-3.6.3 /opt/
```
Then, to add it to the Path run the following:
```
M2_HOME='/opt/apache-maven-3.6.3'
PATH="$M2_HOME/bin:$PATH"
export PATH
```

## Clone Repository
Then, clone the repository on your local machine:
```
git clone https://github.com/EBayego/OnlineShop.git
cd OnlineShop
```

## Databases Configuration
Download PostgreSQL and run the script files located at "{microservice}\src\main\resources\schema.sql".

In your IDE, you will need to set an environment variable. In eclipse you should go to Run Configurations -> Spring Boot App -> 
Create a new one from this project -> Go to the Environment tab -> Add this variable:
``` DATABASE_URL=jdbc:postgresql://localhost:5432/payment_db. ```

Download MongoDB also, and start a new Connection called OnlineShop, and inside, create a new database with Database Name and Collection name "product_db".

## Configure Application Properties
The application properties are located in the "{microservice}/src/main/resources/application.yml" file. Here you can configure the database connection:
```
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/payment_db
    username: postgres
    password: postgres
```

## Kafka Configuration
Download the latest version of Kafka in [this link](https://kafka.apache.org/downloads). Select Source download, and click on the link. Afterwards, extract the content of the file, and inside the folder called "kafka_{version}" execute the following commands to start ZooKeeper and Kafka:
```
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

## Start the Application
Once you have all installed, and Kafka and Zookeeper running, you can start all three microservices by running them in your IDE as Spring Boot App, or by executing the following command in all three:
```
mvn spring-boot:run
```

Also, you will have to run the frontend powered by Angular, by executing this command on "/store-frontend/":
```
ng serve
```

# Navigate through the WebSite & the API
The website will be located in http://localhost:4200/.

To interact with the API endpoints, you have to look at the API documentation, available at:
- http://localhost:8080/swagger-ui.html for the Products Service (Port :8080).
- http://localhost:8081/swagger-ui.html for the Orders Service (Port :8081).
- http://localhost:8082/swagger-ui.html for the Customers Service (Port :8082).
