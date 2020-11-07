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
## Context of the Backend
The context of the backend is `/api`. This is set at `server.servlet.context-path` in 
the application.yaml. Since we serve the backend at `/api`, every controller context 
path is appended after `/api`. eg: `https://host/api/notification`.
The admin portal ui to the backend is served at `/` as a seperate web app. 
Similarly, backend is another webapp served at `/api` in the same tomcat.

## How the Portal Webapp is Served in the Tomcat Server
- pom.xml in `portal/` will build the vue app and will create a war, by including everything inside `dist`
- The backend server during startup will look for a war file,
    - first in a folder named `webapps` parallel to jar 
    - second in the folder `portal/target/`
    - if none of them exists, backend will start without the webapp
- For more info: `lk.gov.govtech.covid19.config.WebappConfiguration`
- Content in the war will appear at localhost:8000/


 