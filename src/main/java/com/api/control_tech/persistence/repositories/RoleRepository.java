package com.api.control_tech.persistence.repositories;

import com.api.control_tech.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
