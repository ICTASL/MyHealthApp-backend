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
 
- replace the `covid-19-lk-dev-firebase-adminsdk.json` file in `/src/main/resources/credentials/` with the private key from Firebase Admin SDK

- build project using `mvn clean install`
- run using: `mvn spring-boot:run`

### APIs 
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
---

## FCM Testing

---
 #### GET /notification – Trigger sample notification with default values sending 
```
curl -H "Content-Type: application/json" -X GET http://localhost:8000/notification
```

#### POST /notification/topic – Send a message to a specific topic

```
curl -d '{"title":"Hello", "message":"The message...", "topic":"contactTopic"}' -H "Content-Type: application/json" -X POST http://localhost:8000/notification/topic
```

#### POST /notification/token – Send a message to a specific device (with the token)

```
curl -d '{"title":"Hey you!", "message":"Watch out!", "token":"cct00ebz8eg:APA91bFcTkFE_0Qafj6nWv5yHxqCLTyxAaqi4QzwsFNLP5M9G78X8Z5UMZTW004q1PUux63Ut-1WMGVToMNTdB3ZfO8lCZlc4lGpxm7LBdWfkhaUxdbpQ5xIO5cAb-w9H2dBLNHT7i-U", "topic": ""}' -H "Content-Type: application/json" -X POST http://localhost:8080/notification/token
```

#### POST /notification/data – Send a message to a specific topic with additional payload data.

```
curl -d '{"title":"Hello", "message":"Data message", "topic":"contactTopic"}' -H "Content-Type: application/json" -X POST http://localhost:8000/notification/data
```

 #### If succeeded you should receive following JSON response with code 200:

```
{
    "status": 200,
    "message": "Notification has been sent."
}
```


#### GET /dashboard/status - Get the status of total case, death case, recovered case and suspect case by Covid-19
```
curl -H "Content-Type: application/json" -X GET http://localhost:8000/application/dashboard/status`
```

If succeeded you should receive following JSON response with code 200:

```
{
    "lk_total_case": 99,
    "lk_recovered_case": 99,
    "lk_total_deaths": 99,
    "lk_total_suspect": 99,
    "last_update_time": "2020-03-17 15:10"
}
```
# How to Start The Application as a Service (Ubuntu)

## Step 1 Create a Service
- *covid-19* : Customizable 
```bash
sudo vim /etc/systemd/system/covid-19.service
```
Copy/paste the following into the file `/etc/systemd/system/covid-19.service`
- *WorkingDirectory* : The directory of the application
- *ExecStart* : The bash script path to start the application
```bash
[Unit]
# Description of the service
Description= COVID-19 Service
[Service]
# The user that should run the service 
User=green
# The configuration file application.properties should be here:
# Change this to your workspace
WorkingDirectory=/home/green/app-service-test
#path to executable. 
#executable is a bash script which calls jar file
ExecStart=/home/green/app-service-test/service
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5
[Install]
WantedBy=multi-user.target
```

## Step 2: Create a Bash Script to Call The Service
- *covid19-1.0.0-SNAPSHOT.jar* : jar file name
```bash
#!/bin/sh
/usr/bin/java -jar covid19-1.0.0-SNAPSHOT.jar server application.yml
```
Give your script execute permission:
```bash
sudo chmod u+x service
```

## Step 3: Enable/Start/Stop the Service

Enable
```bash
sudo systemctl daemon-reload
sudo systemctl enable covid-19.service
```

start
```bash
sudo systemctl start covid-19
```

status
```bash
sudo systemctl status covid-19
```
stop
```bash
sudo systemctl stop covid-19
```

# Web Portal UI

## Getting started

### Setting up Build system
- In order to Setup You need to Run `npm install` to install all the dependencies. 
- Now Run `npm run watch`.
- All of the following folders are monitored for changes, which will tell the browser to reload automatically after any changes are made:
`Resources>Js`
-Now you can edit any html file inside the resource.

- Hit Ctrl+C or just close the command line window to stop the server.

_Happy Contributing!_
