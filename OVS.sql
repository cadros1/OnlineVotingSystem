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
  `publush_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vote_id`),
  FOREIGN KEY (`user_account`) REFERENCES `users` (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

```
CREATE TABLE `questions` (
  `question_id` INT NOT NULL AUTO_INCREMENT,
  `vote_id` INT NOT NULL,
  `question_text` VARCHAR(255) NOT NULL,
  `question_type` TINYINT NOT NULL,
  `required` BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (`question_id`),
  FOREIGN KEY (`vote_id`) REFERENCES `votes` (`vote_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `questions` (`vote_id`, `question_text`, `question_type`, `required`) VALUES (1, '单选题1', 0, TRUE);
INSERT INTO `questions` (`vote_id`, `question_text`, `question_type`, `required`) VALUES (1, '多选题2', 1, TRUE);
INSERT INTO `questions` (`vote_id`, `question_text`, `question_type`, `required`) VALUES (1, '填空题3', 2, TRUE);
INSERT INTO `questions` (`vote_id`, `question_text`, `question_type`, `required`) VALUES (1, '简述题4', 3, FALSE);

CREATE TABLE `options` (
  `option_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `option_text` VARCHAR(255) NOT NULL,
  `is_other` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`option_id`),
  FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (1, '选项1', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (1, '选项2', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (1, '选项3', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (2, '选项1', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (2, '选项2', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (2, '选项3', FALSE);
INSERT INTO `options` (`question_id`, `option_text`, `is_other`) VALUES (2, '其它', TRUE);
```
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
```
CREATE TABLE `question_dependencies` (
  `dependency_id` INT NOT NULL AUTO_INCREMENT,
  `question_id` INT NOT NULL,
  `parent_question_id` INT NOT NULL,
  `trigger_option_id` INT,
  PRIMARY KEY (`dependency_id`),
  FOREIGN KEY (`parent_question_id`) REFERENCES `questions` (`question_id`),
  FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  FOREIGN KEY (`trigger_option_id`) REFERENCES `options` (`option_id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO `question_dependencies` (`question_id`, `parent_question_id`) VALUES (2, 1);
INSERT INTO `question_dependencies` (`question_id`, `parent_question_id`) VALUES (3, 2);
INSERT INTO `question_dependencies` (`question_id`, `parent_question_id`, `trigger_option_id`) VALUES (1, 4, 3);
```