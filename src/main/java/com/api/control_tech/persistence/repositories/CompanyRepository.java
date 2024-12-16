package com.api.control_tech.persistence.repositories;

import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findByUserId(Long userId);
    boolean existsByNameAndUser(String name, User user);
}
