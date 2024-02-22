package org.example.ssiach11ex1s1.repository;

import org.example.ssiach11ex1s1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByUsername(String username);

}
