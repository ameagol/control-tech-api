package com.api.control_tech.persistence.repositories;

import com.api.control_tech.persistence.entities.Status;
import com.api.control_tech.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface StatusRepository extends JpaRepository<Status,Long> {
    Optional<Status> findByName(String name);
    boolean existsByNameAndUser(String name, User user);
    List<Status> findByUserId(Long id);
}
