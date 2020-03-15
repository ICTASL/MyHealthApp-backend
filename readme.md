# COVID-19 Backend Services

## How to run
- install mysql server
- execute `covid19.sql` on server (execute `covid19_test.sql` to add test data)
- build project using `mvn clean install`
- run using: `mvn spring-boot:run`

### APIs 
GET ``http://localhost:8000/application/message/<messageId>/<lang>``

options for lang: en, si, ta

----
GET ``http://localhost:8000/application/message/latest``

returns integer as response

----
