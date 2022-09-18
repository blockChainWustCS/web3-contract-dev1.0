CREATE DATABASE IF NOT EXISTS `scoredbv2`; 
USE `scoredbv2`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) NOT NULL,
  `isAdmin` BOOLEAN DEFAULT FALSE,
  `name` varchar(255) ,
  `num` varchar(255) ,
  `age` int(11) ,
  `url` varchar(255),
  PRIMARY KEY (`id`),
  UNIQUE KEY `address` (`address`),
   UNIQUE KEY `num` (`num`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `course`(
  `courseID` INT(10) NOT NULL,
  `name` varchar(255) ,
  `teacheraddress` varchar(255) NOT NULL,
  
  PRIMARY KEY (`courseID`),
  UNIQUE KEY `name` (`name`),
  FOREIGN KEY(`teacheraddress`) REFERENCES user(`address`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `score`(
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `courseid` INT(10) NOT NULL,
  `address` varchar(255) NOT NULL,
  `score` varchar(255),
  PRIMARY KEY (`id`),
  FOREIGN KEY(`address`) REFERENCES user(`address`),
  FOREIGN KEY(`courseid`) REFERENCES course(`courseID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
