CREATE DATABASE OVS;

USE OVS;

CREATE TABLE `users` (
  `account` VARCHAR(20) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `users` (`account`, `password`, `username`) VALUES ('admin', 'admin', 'admin');
INSERT INTO `users` (`account`, `password`, `username`) VALUES ('111', '111', '高洪森');
INSERT INTO `users` (`account`, `password`, `username`) VALUES ('222', '222', '李祖怡');
INSERT INTO `users` (`account`, `password`, `username`) VALUES ('333', '333', '王奕轩');

CREATE TABLE `votes` (
  `vote_id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(50) NOT NULL,
  `description` VARCHAR(255) DEFAULT "该问卷暂时没有描述",
  `user_account` VARCHAR(20) NOT NULL,
  `root_question_id` INT,
  `is_public` BOOLEAN NOT NULL,
  `publish_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vote_id`),
  FOREIGN KEY (`user_account`) REFERENCES `users` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `answeredUsers` (
  `vote_id` INT NOT NULL,
  `user_account` VARCHAR(20) NOT NULL,
  `answer_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vote_id`, `user_account`),
  FOREIGN KEY (`vote_id`) REFERENCES `votes` (`vote_id`),
  FOREIGN KEY (`user_account`) REFERENCES `users` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

CREATE TABLE `answers` (
  `vote_id` INT NOT NULL,
  `question_id` INT NOT NULL,
  `user_account` VARCHAR(20) NOT NULL,
  `selected_option_id` INT,
  `custom_answer` VARCHAR(255),
  PRIMARY KEY (`vote_id`, `question_id`, `user_account`),
  FOREIGN KEY (`vote_id`) REFERENCES `votes` (`vote_id`),
  FOREIGN KEY (`user_account`) REFERENCES `users` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;