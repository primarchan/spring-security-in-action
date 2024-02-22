package org.example.ssiach11ex1s1.repository;

import org.example.ssiach11ex1s1.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, String > {

    Optional<Otp> findOtpByUsername(String username);

}
