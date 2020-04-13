# MyHealth - Backend - Spring Boot

## How to Run
```
mvn spring-boot:run
```
or
```
mvn clean package
cd target
java -jar backend.jar
```

## How the Frontend is Served in the Tomcat Server
- pom.xml in admin-webapp will create a war, by including everything inside dist
- The backend server during startup will look for a war file,
    - first in the folder where the jar is 
    - second in admin-webapp/target
    - if non of them exist, backend will start without the webapp
- For more info: `lk.gov.govtech.covid19.config.WebappConfiguration`
- Content in the war will appear at localhost:8000/portal/
 