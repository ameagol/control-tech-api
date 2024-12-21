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
import java.util.UUID;

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

        Role userRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));

        User savedUser = Optional.of(subscribeDto)
                .map(dto -> {
                    User user = new User();
                    user.setName(dto.getEmail());
                    user.setPassword(passwordEncoder.encode(dto.getPassword()));
                    user.setEmail(dto.getEmail());
                    user.setRoles(Set.of(userRole));
                    user.setResetHash(UUID.randomUUID().toString());
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
                .filter(dto -> !userRepository.existsByEmail(dto.getEmail()))
                .orElseThrow(() -> new UserAlreadyExistsException("User already exists"));

        Optional.of(subscribeDto.getPassword())
                .filter(password -> password.length() >= 8)
                .orElseThrow(() -> new InvalidPasswordException("Password must be at least 8 characters long"));

        Optional.of(subscribeDto.getCompany())
                .filter(company -> company.length() <= 30 && !company.isEmpty())
                .orElseThrow(() -> new InvalidCompanyException("Company name must be between 1 and 30 characters"));
    }
}

