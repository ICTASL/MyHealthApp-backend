#contains test data

use covid19_db;

#notification table
INSERT INTO `covid19_db`.`notification` (`title`, `subtitle`, `source`, `message_en`, `message_si`, `message_ta`) VALUES ('Test news item', 'test subtitle', 'MOH', 'This is a test message', 'මෙය පරීක්ෂණ පණිවිඩයකි', 'இது ஒரு சோதனை செய்தி');
INSERT INTO `covid19_db`.`notification` (`title`, `subtitle`, `source`, `message_en`, `message_si`, `message_ta`) VALUES ('Test news item', 'test subtitle', 'MOH', '18 confirmed cases of COVID-19 found in sri lanka', 'ශ්‍රී ලංකාවෙන් සොයාගත් COVID-19 රෝගීන් 18 ක්', 'ஸ்ரீலங்காவில் 18 கோவிட் -19 வழக்குகள் உறுதி செய்யப்பட்டன');

INSERT INTO `covid19_db`.`covid_status` (`id`,`lk_total_case`,`lk_recovered_case`,`lk_total_deaths`,`lk_total_suspect`,`last_update_time`) VALUES (1,99,99,99,99,'2020-03-17 15:10');


#notification_message_type table
