drop DATABASE if exists spring;

create DATABASE spring default CHARACTER SET = UTF8;

DROP TABLE IF EXISTS `spring`.`noticeboard`;
CREATE TABLE  `spring`.`noticeboard` (
  `nbId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nbSubject` varchar(255) NOT NULL,
  `nbContent` text NOT NULL,
  `nbReadCount` int(10) unsigned NOT NULL DEFAULT '0',
  `nbInsertDate` datetime NOT NULL,
  PRIMARY KEY (`nbId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

