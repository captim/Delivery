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
    `np_warehouse_id` VARCHAR(40) NOT NULL,
    PRIMARY KEY (`shipping_address_id`)
);


CREATE TABLE `delivery_db`.`requests`
(
    `request_id`     INT         NOT NULL AUTO_INCREMENT,
    `user_id`         INT         NOT NULL,
    `creation_date`   DATETIME    NOT NULL,
    `status_id`       INT         NOT NULL,
    `delivery_number` VARCHAR(45) NOT NULL,
    `package_size_id` INT NOT NULL,
    `transaction_id` VARCHAR(45) NOT NULL,
    `shipping_address_id` INT,
    PRIMARY KEY (`request_id`)
);

CREATE TABLE `delivery_db`.`roles` (
    `role_id` INT NOT NULL,
    `role_name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`role_id`));

CREATE TABLE `delivery_db`.`package_sizes` (
    `package_size_id` INT NOT NULL,
    `name` VARCHAR(45) NOT NULL,
    `max_weight` INT NOT NULL,
    `price` FLOAT NOT NULL,
    PRIMARY KEY (`package_size_id`));

CREATE TABLE `delivery_db`.`statuses` (
    `status_id` INT NOT NULL,
    `status_name` VARCHAR(45) NOT NULL,
    `message` VARCHAR(45) NOT NULL,
    `short_message` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`status_id`));


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
    ADD INDEX `shipping_address_request_idx` (`shipping_address_id` ASC) VISIBLE;
ALTER TABLE `delivery_db`.`requests`
    ADD CONSTRAINT `shipping_address_request`
        FOREIGN KEY (`shipping_address_id`)
            REFERENCES `delivery_db`.`shipping_addresses` (`shipping_address_id`)
            ON DELETE RESTRICT
            ON UPDATE CASCADE;
