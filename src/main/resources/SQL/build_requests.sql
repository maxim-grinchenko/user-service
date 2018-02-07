CREATE DATABASE `university`
  DEFAULT CHARSET utf8;

CREATE TABLE `role_ref` (
  `id`   INT(11)     NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_ref_un` (`name`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

CREATE TABLE `users_table` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(60)  NOT NULL,
  `last_name`   VARCHAR(128) NOT NULL,
  `middle_name` VARCHAR(128) NOT NULL,
  `phone`       VARCHAR(32)  NOT NULL,
  `email`       VARCHAR(32)  NOT NULL,
  `login`       VARCHAR(32)  NOT NULL,
  `password`    VARCHAR(32)  NOT NULL,
  `role_id`     INT(11)      NOT NULL,
  `created_aT`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at`  TIMESTAMP    NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `users_table_un_login` (`login`),
  UNIQUE KEY `users_table_un_phone` (`phone`),
  UNIQUE KEY `users_table_un_email` (`email`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_table_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role_ref` (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;

INSERT INTO university.role_ref (id, name) VALUES (1, 'ADMIN');
INSERT INTO university.role_ref (id, name) VALUES (2, 'USER');