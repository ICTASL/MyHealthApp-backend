CREATE DATABASE IF NOT EXISTS covid19_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

use covid19_db;

#insert create statements below as needed

CREATE TABLE `notification` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `subtitle` VARCHAR(100) NULL,
  `source` VARCHAR(45) NULL,
  `message_en` VARCHAR(500) NULL,
  `message_si` VARCHAR(500) NULL,
  `message_ta` VARCHAR(500) NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `epid_case` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `case_number` VARCHAR(100) NULL,
  `is_local` TINYINT(1) NULL,
  `detected_from` VARCHAR(45) NULL,
  `message_en` VARCHAR(500) NULL,
  `message_si` VARCHAR(500) NULL,
  `message_ta` VARCHAR(500) NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
  ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `epid_location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `case_id` INT NULL,
  `date` DATE NULL,
  `area` VARCHAR(100) NULL,
  `longitude` VARCHAR(45) NULL,
  `latitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `notification_message_type` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
CREATE TABLE `covid_status` (
  `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `lk_total_case` INT NOT NULL,
  `lk_recovered_case` INT NOT NULL,
  `lk_total_deaths` INT NOT NULL,
  `lk_total_suspect` INT NOT NULL,
  `last_update_time` timestamp default current_timestamp NOT NULL on update current_timestamp);

INSERT INTO `covid_status` (`id`,`lk_total_case`,`lk_recovered_case`,`lk_total_deaths`,`lk_total_suspect`,`last_update_time`) VALUES (1,99,99,99,99, NOW());
