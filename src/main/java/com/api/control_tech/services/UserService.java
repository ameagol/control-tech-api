package com.api.control_tech.services;

import com.api.control_tech.exceptions.InvalidCompanyException;
import com.api.control_tech.exceptions.InvalidPasswordException;
import com.api.control_tech.exceptions.RoleNotFoundException;
import com.api.control_tech.exceptions.UserAlreadyExistsException;
import com.api.control_tech.models.SubscribeDto;
import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.entities.Role;
import com.api.control_tech.persistence.entities.User;
import com.api.control_tech.persistence.repositories.CompanyRepository;
import com.api.control_tech.persistence.repositories.RoleRepository;
import com.api.control_tech.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SubscribeDto registerUser(SubscribeDto subscribeDto) {
        validateUser(subscribeDto);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User savedUser = Optional.of(subscribeDto)
                .map(dto -> {
                    User user = new User();
                    user.setUserName(dto.getUsername());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    user.setEmail(dto.getUsername());
                    user.setRoles(Set.of(userRole));
                    return user;
                })
                .map(userRepository::save)
                .orElseThrow(() -> new IllegalStateException("Failed to save user"));

        // Create and save the company
        Optional.of(subscribeDto)
                .map(SubscribeDto::getCompany)
                .map(companyName -> {
                    Company company = new Company();
                    company.setName(companyName);
                    company.setUser(savedUser);
                    return company;
                })
                .ifPresent(companyRepository::save);

        return Optional.of(subscribeDto)
                .map(dto -> {
                    dto.setPassword(null);
                    return dto;
                })
                .orElseThrow(() -> new IllegalStateException("Failed to prepare response"));
    }

    private void validateUser(SubscribeDto subscribeDto) {
        Optional.ofNullable(subscribeDto)
                .filter(dto -> !userRepository.existsByUserName(dto.getUsername()))
                .orElseThrow(() -> new UserAlreadyExistsException("User already exists"));

        Optional.of(subscribeDto.getPassword())
                .filter(password -> password.length() >= 8)
                .orElseThrow(() -> new InvalidPasswordException("Password must be at least 8 characters long"));

        Optional.of(subscribeDto.getCompany())
                .filter(company -> company.length() <= 30 && !company.isEmpty())
                .orElseThrow(() -> new InvalidCompanyException("Company name must be between 1 and 30 characters"));
    }
}

