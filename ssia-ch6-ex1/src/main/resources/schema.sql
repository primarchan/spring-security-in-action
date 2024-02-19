-- 사용자 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`user` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `username` VARCHAR(45) NOT NULL,
     `password` TEXT NOT NULL,
     `algorithm` VARCHAR(45) NOT NULL,
     PRIMARY KEY (`id`)
);

-- 권한 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`authority` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `user` INT NOT NULL,
    PRIMARY KEY (`id`)
);

--
-- 상품 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`product` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `name` VARCHAR(45) NOT NULL,
     `price` VARCHAR(45) NOT NULL,
     `currency` VARCHAR(45) NOT NULL,
     PRIMARY KEY (`id`)
);