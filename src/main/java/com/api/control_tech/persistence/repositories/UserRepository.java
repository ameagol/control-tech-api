package com.api.control_tech.persistence.repositories;

import com.api.control_tech.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);

    Optional<User> findByEmail(String email);
}