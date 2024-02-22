-- User 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`user` (
     `username` VARCHAR(45) NOT NULL,
     `password` TEXT NULL,
     PRIMARY KEY (`username`)
);

-- OTP 테이블 생성
CREATE TABLE IF NOT EXISTS `spring_security`.`otp` (
   `username` VARCHAR(45) NOT NULL,
    `code` VARCHAR(45) NULL,
    PRIMARY KEY (`username`)
);