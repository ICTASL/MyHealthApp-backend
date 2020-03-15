# COVID-19 Backend Services

## How to run
- install mysql server
- execute `covid19.sql` on server
- build project using `mvn clean install`
- run using: `mvn spring-boot:run`

### APIs 
GET ``http://localhost:8000/application/message/<messageId>/<lang>``

options for lang: en, si, ta

Sample db entry:
```
INSERT INTO `covid19_db`.`hpb_notification` (`id`, `title`, `subtitle`, `source`, `message_en`, `message_si`, `message_ta`) VALUES ('1', 'Test news item', 'test subtitle', 'MOH', 'This is a test message', 'මෙය පරීක්ෂණ පණිවිඩයකි', 'இது ஒரு சோதனை செய்தி');
```

----
GET ``http://localhost:8000/application/message/latest``

returns integer as response

----
