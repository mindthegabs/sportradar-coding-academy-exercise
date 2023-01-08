#create.sql for event calendar

#drop existing tables
drop table if exists event;
drop table if exists competitor;
drop table if exists sport;
drop table if exists venue;

#create tables
CREATE TABLE `sport` (
  `sport_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`sport_id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
);

CREATE TABLE `venue` (
  `venue_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`venue_id`)
) ;

CREATE TABLE `competitor` (
  `competitor_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `_sport_id` int DEFAULT NULL,
  PRIMARY KEY (`competitor_id`),
  FOREIGN KEY (`_sport_id`) REFERENCES `sport` (`sport_id`)
) ;

CREATE TABLE `event` (
  `event_id` int NOT NULL AUTO_INCREMENT,
  `_sport_id` int NOT NULL,
  `date_time` datetime NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `_competitor1_id` int DEFAULT NULL,
  `_competitor2_id` int DEFAULT NULL,
  `_venue_id` int DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  FOREIGN KEY (`_competitor1_id`) REFERENCES `competitor` (`competitor_id`),
  FOREIGN KEY (`_competitor2_id`) REFERENCES `competitor` (`competitor_id`),
  FOREIGN KEY (`_sport_id`) REFERENCES `sport` (`sport_id`),
  FOREIGN KEY (`_venue_id`) REFERENCES `venue` (`venue_id`)
  );
