[![Build Status](https://travis-ci.org/azhamn/COVID-19.svg?branch=master)](https://travis-ci.org/azhamn/COVID-19)
----
# COVID-19 Backend Services

## Contributing
Please keep the following in mind when submitting your valuable contributions 😊
- Currently, all development is happening out of the master branch, so kindly fork it and make your changes there before submitting a PR.
- If you've implemented a new API, update the readme and [postman collection](../master/postman/COVID-19.postman_collection.json). This makes it so much easier for other developers to consume your APIs.
- Rebase your code before submitting PRs: `git pull --rebase upstream master` ensures there are no conflicts and keeps the tree clean!
- Comments are always encouraged - you never know who'll continue to work on your code next, so let's make their life a bit easier shall we 😉
- Finally, see something wrong or have a suggestion? raise an [issue](https://github.com/azhamn/COVID-19/issues) so we can get working on it right away!

----

## How to run
- install mysql server
- execute `covid19.sql` on server (execute `covid19_test.sql` to add test data)

### For Frontend Development
- Open the folder portal-frontend directly from the IDE
- Refer to the readme inside portal-frontend
 
### For Backend Development
- replace the `covid-19-lk-dev-firebase-adminsdk.json` file in `/src/main/resources/credentials/` with the private key from Firebase Admin SDK
- `cd backend`
- run using: `mvn spring-boot:run`
- change `firebase.topic` in `application.yml` accordingly. `mobile_message` is used for production and `mobile_message_test` is used for testing
- Replace `/src/main/resources/credentials/covid-19-lk-dev-firebase-adminsdk.json` with a valid credential file.

### Run as a Complete Package
- build project using `mvn clean install`
- unzip the `assembly/target/myhealth-server-1.0.0.zip`
- run `java -jar myhealth-server.jar`
 
### Run with Docker
- build project using `mvn clean install`
- unzip the `assembly/target/myhealth-server-1.0.0.zip`
- modify the db url in the application.yml (replace localhose with container name) as below  (FIXME: automate with spring profiles)
- `url: jdbc:mysql://db:3306/covid19_db?useUnicode=yes&characterEncoding=UTF-8`
- run ` docker-compose up -d`  , make sure that ports 8000 and 3306 are not used in the local machine
- connect to dockerized mysql localhost:3306/covid19_db and execute `covid19.sql` on server (execute `covid19_test.sql` to add test data)

## APIs 
----
#### Get Alert by Id

GET ``http://localhost:8000/api/application/alert/<alertId>/<lang>``

options for lang: en, si, ta

----
#### Get Latest Alert Id
GET ``http://localhost:8000/api/application/alert/latest``

returns integer as response

----
#### Add New Alert

```
POST http://localhost:8000/api/notification/alert/add 

{
   "title":"Test title",
   "subtitle":"Test subtitle",
   "source":"MOH",
   "messageEn":"This is a test message",
   "messageSi":"මෙය පරීක්ෂණ පණිවිඩයකි",
   "messageTa":"இது ஒரு சோதனை செய்தி"
}
```

----
#### Get Case By Id

GET ``http://localhost:8000/api/application/case/<caseId>/<lang>``

options for lang: en, si, ta

----
#### Get Latest Case Id
GET ``http://localhost:8000/api/application/case/latest``

returns integer as response

----
#### Add new Case
```
POST http://localhost:8000/api/notification/case/add

{
   "caseNumber":"DHIS/WP/COL/1234",
   "locations":[
      {
         "date":"2020/02/02",
         "from":"2020/02/02 00:00:00",
         "to":"2020/02/02 11:59:59",
         "address":"Colombo Municipal Council",
         "longitude":"1234",
         "latitude":"4321"
      },
      {
         "date":"2020/03/02",
         "from":"2020/03/02 13:00:00",
         "to":"2020/03/02 15:45:00",
         "address":"Galadari Hotel",
         "longitude":"1234",
         "latitude":"4321"
      }
   ],
   "message_en":"Person confirmed with COVID-19",
   "message_si":"COVID-19 සමඟ තහවුරු කළ පුද්ගලයා",
   "message_ta":"COVID-19 உடன் நபர் உறுதிப்படுத்தப்பட்டார்"
}
```

#### Get Status

```
GET http://localhost:8000/api/application/dashboard/status
```

If succeeded you should receive following JSON response with code `200`:

```
{
    "lk_total_case": 99,
    "lk_recovered_case": 99,
    "lk_total_deaths": 99,
    "lk_total_suspect": 99,
    "last_update_time": "2020-03-17 15:10"
}
```
----
#### Update Dashboard Status
```
PUT http://localhost:8000/api/application/dashboard/status

{
    "lk_total_case": 98,
    "lk_recovered_case": 98,
    "lk_total_deaths": 99,
    "lk_total_suspect": 99
}
```

_Happy Contributing!_
