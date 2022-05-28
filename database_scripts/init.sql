CREATE TABLE `delivery_db`.`users`
(
    `user_id`             INT         NOT NULL AUTO_INCREMENT,
    `first_name`          VARCHAR(45) NOT NULL,
    `last_name`           VARCHAR(45) NOT NULL,
    `patronymic`          VARCHAR(45) NOT NULL,
    `shipping_address_id` INT         NULL,
    `email`               VARCHAR(45) NOT NULL,
    `password`            VARCHAR(45) NOT NULL,
    `role_id`             INT         NOT NULL,
    `username`            VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_id`)
);


CREATE TABLE `delivery_db`.`shipping_addresses`
(
    `shipping_address_id`    INT         NOT NULL AUTO_INCREMENT,
    `shipping_address_value` VARCHAR(75) NOT NULL,
    PRIMARY KEY (`shipping_address_id`)
);


CREATE TABLE `delivery_db`.`requests`
(
    `requests_id`     INT         NOT NULL AUTO_INCREMENT,
    `user_id`         INT         NOT NULL,
    `creation_date`   DATETIME    NOT NULL,
    `status_id`       INT         NOT NULL,
    `delivery_number` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`requests_id`)
);


CREATE TABLE `delivery_db`.`statuses`
(
    `status_id`          INT          NOT NULL AUTO_INCREMENT,
    `status_name`        VARCHAR(30)  NOT NULL,
    `status_description` VARCHAR(150) NOT NULL,
    PRIMARY KEY (`status_id`)
);


-- INDEXES/FOREIGN_KEYS --

ALTER TABLE `delivery_db`.`users`
    ADD INDEX `shipping_address_id_idx` (`shipping_address_id` ASC) VISIBLE;
ALTER TABLE `delivery_db`.`users`
    ADD CONSTRAINT `shipping_address_id` FOREIGN KEY (`shipping_address_id`)
        REFERENCES `delivery_db`.`shipping_addresses` (`shipping_address_id`)
        ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE `delivery_db`.`requests`
    ADD INDEX `user_id_idx` (`user_id` ASC) VISIBLE;
ALTER TABLE `delivery_db`.`requests`
    ADD CONSTRAINT `user_id` FOREIGN KEY (`user_id`)
        REFERENCES `delivery_db`.`users` (`user_id`)
        ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `delivery_db`.`requests`
    ADD INDEX `status_id_idx` (`status_id` ASC) VISIBLE;
ALTER TABLE `delivery_db`.`requests`
    ADD CONSTRAINT `status_id` FOREIGN KEY (`status_id`)
        REFERENCES `delivery_db`.`statuses` (`status_id`)
        ON DELETE RESTRICT ON UPDATE RESTRICT;

