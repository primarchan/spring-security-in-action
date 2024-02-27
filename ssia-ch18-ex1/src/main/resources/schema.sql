CREATE TABLE IF NOT EXISTS `spring_security`.`workout` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user` VARCHAR(45) NULL,
    `start` DATETIME NULL,
    `end` DATETIME NULL,
    `difficulty` INT NULL,
    PRIMARY KEY (`id`)
);