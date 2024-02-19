-- 사용자 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`users` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `username` VARCHAR(45) NOT NULL,
     `password` VARCHAR(45) NOT NULL,
     `enabled` INT NOT NULL,
     PRIMARY KEY (`id`),
     UNIQUE KEY `unique_username` (`username`)
);

-- 권한 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`authorities` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `authority` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_authority` (`username`, `authority`)
);