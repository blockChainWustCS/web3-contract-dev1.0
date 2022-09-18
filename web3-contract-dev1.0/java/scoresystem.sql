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

insert into user(address,isAdmin,name,num,age,url) values("0xf39Fd6e51aad88F6F4ce6aB8827279cffFb92266",1,"admin","202012340000",30,"http://47.110.146.5/pic.jpg");
insert into user(address,isAdmin,name,num,age,url) values("0x12c9783748240FFa5b5c9dFB0ba6bB19848Bd626",0,"zzc","202213421818",20,"http://47.110.146.5/pic.jpg");
insert into user(address,isAdmin,name,num,age,url) values("0xFE3B557E8Fb62b89F4916B721be55cEb828dBd73",0,"ljy","202213422034",20,"http://47.110.146.5/pic.jpg");
insert into user(address,isAdmin,name,num,age,url) values("0x4937B82d86259965ab4c004b347cF5a96e8E7804",0,"xjb","202134512234",21,"http://47.110.146.5/pic.jpg");