drop DATABASE if exists spring;

create DATABASE spring default CHARACTER SET = UTF8;

DROP TABLE IF EXISTS `spring`.`noticeboard`;
CREATE TABLE  `spring`.`noticeboard` (
  `nbId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nbSubject` varchar(255) NOT NULL,
  `nbContent` text NOT NULL,
  `nbReadCount` int(10) unsigned NOT NULL DEFAULT '0',
  `nbInsertDate` datetime NOT NULL,
  `nbCommentCount` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`nbId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `spring`.`noticeboardcomment`;
CREATE TABLE  `spring`.`noticeboardcomment` (
  `nbcId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nbId` int(10) unsigned NOT NULL,
  `nbcComment` text NOT NULL,
  `nbcInsertDate` datetime NOT NULL,
  PRIMARY KEY (`nbcId`),
  KEY `FK_noticeboardcomment_1` (`nbId`),
  CONSTRAINT `FK_noticeboardcomment_1` FOREIGN KEY (`nbId`) REFERENCES `noticeboard` (`nbId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

