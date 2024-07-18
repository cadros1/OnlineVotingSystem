CREATE DATABASE OVS;

USE OVS;

CREATE TABLE `users` (
  `account` VARCHAR(20) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `users` (`account`, `password`, `username`,) VALUES ('admin', 'admin', 'admin');
InSERT INTO `users` (`account`, `password`, `username`,) VALUES ('111', '111', '高洪森');
InSERT INTO `users` (`account`, `password`, `username`,) VALUES ('222', '222', '李祖怡');
InSERT INTO `users` (`account`, `password`, `username`,) VALUES ('333', '333', '王奕轩');