DROP DATABASE jdevson93;
CREATE DATABASE jdevson93;
USE jdevson93;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS engine;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS entry;

CREATE TABLE IF NOT EXISTS car (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `brand` VARCHAR(255) DEFAULT NULL,
  `model` VARCHAR(255) DEFAULT NULL,
  `production_start_date` YEAR DEFAULT NULL,
  `production_end_date` YEAR DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS engine (
  `id` INT(11) unsigned NOT NULL AUTO_INCREMENT,
  `engine_size` VARCHAR(255) DEFAULT NULL,
  `engine_power` VARCHAR(255) DEFAULT NULL,
  `fuel_type` VARCHAR(255) DEFAULT NULL,
  `engine_symbol` VARCHAR(255) DEFAULT NULL,
  `car_id` INT(11) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY fk_car_id (`car_id`),
  CONSTRAINT fk_car_id FOREIGN KEY (`car_id`) REFERENCES car (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(255) DEFAULT NULL,
  email VARCHAR(255) DEFAULT NULL,
  password VARCHAR(255) DEFAULT NULL,
  role varchar(45) DEFAULT NULL,
  enabled TINYINT(1) DEFAULT 1 NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS entry (
  `id` VARCHAR(255) NOT NULL,
  `user_id` VARCHAR(255) NOT NULL,
  `engine_id` INT(11)UNSIGNED NOT NULL,
  `city_value` VARCHAR(255) NOT NULL,
  `highway_value` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY fk_user_id (`user_id`),
  CONSTRAINT fk_user_id FOREIGN KEY (`user_id`) REFERENCES user (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  KEY fk_engine_id (`engine_id`),
  CONSTRAINT fk_engine_id FOREIGN KEY (`engine_id`) REFERENCES engine (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO user (id,name,email,password,role,enabled)
VALUES('cjdsk','Dominik','email2@domain.com','12345','ROLE_ADMIN',1);
INSERT INTO user (id,name,email,password,role,enabled)
VALUES('a','Jan','email@domain.com','54321','ROLE_USER',1);

INSERT INTO car (id, brand, model, production_start_date, production_end_date)
VALUES (1, 'BMW', 'e46', 1998, 2004);
INSERT INTO car (id, brand, model, production_start_date, production_end_date)
VALUES (2, 'KIA', 'Carens IV', 2013, 2017);
INSERT INTO car (id, brand, model, production_start_date, production_end_date)
VALUES (3, 'Subaru', 'Forester III', 2008, 2013);
INSERT INTO car (id, brand, model, production_start_date, production_end_date)
VALUES (4, 'Skoda', 'Octavia II', 2004, 2013);

INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (1, '2.0', '140 hp', 'diesel', '318d', 1);
INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (2, '2.5', '190 hp', 'diesel', '320d', 1);

INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (3, '1.7', '115 hp', 'diesel', 'VGT', 2);
INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (4, '1.6', '135 hp', 'petrol', 'GDI', 2);

INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (5, '2.0', '150 hp', 'petrol', '', 3);
INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (6, '2.5', '171 hp', 'petrol', '', 3);

INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (7, '1.6', '102 hp', 'petrol', 'MPI', 4);
INSERT INTO engine (id, engine_size, engine_power, fuel_type, engine_symbol, car_id)
VALUES (8, '1.6', '102 hp', 'petrol', 'MPI', 4);

INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("y7r4w38i", 'cjdsk', 3, '5.6', '4.8');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("y78yy78hggf", 'cjdsk', 6, '6.7', '5.8');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("tyf7tyf76", 'cjdsk', 2, '8.6', '6.2');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("ftyufuio", 'a', 6, '5.7', '7.4');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("f67f76rf67", 'a', 7, '6.5', '8.2');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("fgvytfdte5s54478", 'a', 8, '9.3', '9.4');
INSERT INTO entry (id, user_id, engine_id, city_value, highway_value)
VALUES ("f6fr6757tg", 'a', 1, '7.8', '11.3');



SET FOREIGN_KEY_CHECKS = 1;
