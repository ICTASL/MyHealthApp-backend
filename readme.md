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
 
### Using the Maven Plugin
- replace the `covid-19-lk-dev-firebase-adminsdk.json` file in `/src/main/resources/credentials/` with the private key from Firebase Admin SDK
- run using: `mvn spring-boot:run`
- change `firebase.topic` in `application.yml` accordingly. `mobile_message` is used for production and `mobile_message_test` is used for testing

### As a Packaged Application
- build project using `mvn clean install`
- copy `application.yml` to the folder where the jar is (`target` if it has not been moved)
- Replace `/src/main/resources/credentials/covid-19-lk-dev-firebase-adminsdk.json` with a valid credential file.
- go into the folder where the jar is (eg. `cd target`)
- run `java -jar covid19-1.0.0-SNAPSHOT.jar`. Check if the jar version matches.
- NOTE: access logs will get stored in the tomcat folder (tomcat/access/).
 These logs can be disabled from the application.yml
 
### with Docker
- build project using `mvn clean install`
- unzip the `target/covid19-1.0.0-SNAPSHOT.zip` to `target/covid19-1.0.0-SNAPSHOT`
- modify the db url in the application.yml (replace localhose with container name) as below  (FIXME: automate with spring profiles)
- `url: jdbc:mysql://db:3306/covid19_db?useUnicode=yes&characterEncoding=UTF-8`
- run ` docker-compose up -d`  , make sure that ports 8000 and 3306 are not used in the local machine
- connect to dockerized mysql localhost:3306/covid19_db and execute `covid19.sql` on server (execute `covid19_test.sql` to add test data)

## APIs 
----
#### Get Alert by Id

GET ``http://localhost:8000/application/alert/<alertId>/<lang>``

options for lang: en, si, ta

----
#### Get Latest Alert Id
GET ``http://localhost:8000/application/alert/latest``

returns integer as response

----
#### Add New Alert

```
POST http://localhost:8000/notification/alert/add 

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

GET ``http://localhost:8000/application/case/<caseId>/<lang>``

options for lang: en, si, ta

----
#### Get Latest Case Id
GET ``http://localhost:8000/application/case/latest``

returns integer as response

----
#### Add new Case
```
POST http://localhost:8000/notification/case/add

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
GET http://localhost:8000/application/dashboard/status
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
PUT http://localhost:8000/application/dashboard/status

{
    "lk_total_case": 98,
    "lk_recovered_case": 98,
    "lk_total_deaths": 99,
    "lk_total_suspect": 99
}
```

## Web Portal UI

### Getting started

#### Setting up Build system
- In order to Setup You need to Run `npm install` to install all the dependencies.
- Now Run `npm run watch`.
- All of the following folders are monitored for changes, which will tell the browser to reload automatically after any changes are made:
`Resources>Js`
-Now you can edit any html file inside the resource.

- Hit Ctrl+C or just close the command line window to stop the server.

#### Adding a New Web Page
- Create a html file in `src/main/resources/templates/`
- Create an endpoint in `WebPortalController` to serve the web page
- The endpoint must return a string which is the name of the html file (eg. `login` for login.html)

_Happy Contributing!_
