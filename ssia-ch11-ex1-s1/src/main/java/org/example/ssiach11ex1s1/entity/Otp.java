package org.example.ssiach11ex1s1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * OTP (One-Time Password)
 */
@Getter
@Setter
@Entity
public class Otp {

    @Id
    private String username;

    private String code;

}
