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

## How the Portal Webapp is Served in the Tomcat Server
- pom.xml in `portal/` will build the vue app and will create a war, by including everything inside `dist`
- The backend server during startup will look for a war file,
    - first in a folder named `webapps` parallel to jar 
    - second in the folder `portal/target/`
    - if none of them exists, backend will start without the webapp
- For more info: `lk.gov.govtech.covid19.config.WebappConfiguration`
- Content in the war will appear at localhost:8000/
 