create database ralomok;

use ralomok;

CREATE TABLE `user` (
  `id` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `name` varchar(10) NOT NULL,
  `win` int DEFAULT '0',
  `lose` int DEFAULT '0',
  `rating` int DEFAULT '1000',
  PRIMARY KEY (`id`)
);
insert into user (id, password, name)
value ("kbumk123", "123321", "김종범");
insert into user (id, password, name)
value ("ssafy", "1234", "김싸피");

CREATE TABLE `stone` (
  `sequence` int NOT NULL AUTO_INCREMENT,
  `boardNum` int NOT NULL,
  `x` int DEFAULT '0',
  `y` int DEFAULT '0',
  `type` int DEFAULT NULL,
  PRIMARY KEY (`sequence`)
) ;

select * from user;

select * from stone;