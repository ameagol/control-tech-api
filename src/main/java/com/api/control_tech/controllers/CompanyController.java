package com.api.control_tech.controllers;

import com.api.control_tech.exceptions.InvalidCompanyException;
import com.api.control_tech.models.CompanyDto;
import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.repositories.CompanyRepository;
import com.api.control_tech.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Company> createCompany(@RequestBody CompanyDto company) {
        return Optional.of(company)
                .flatMap(c -> userRepository.findByEmail(c.getUser().getEmail())
                        .map(user -> {
                            if (companyRepository.existsByNameAndUser(c.getName(), user)) {
                                throw new InvalidCompanyException("Company name already exists for this user.");
                            }
                            return companyRepository.save(new Company(c.getName(), user));
                        }))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidCompanyException("Failed to Create Company"));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long id, @RequestBody CompanyDto companyDto) {
        return companyRepository.findById(id)
                .map(existingCompany -> userRepository.findByEmail(companyDto.getUser().getEmail())
                        .map(user -> {
                            existingCompany.setName(companyDto.getName());
                            existingCompany.setUser(user);
                            Company updatedCompany = companyRepository.save(existingCompany);
                            // Convert to DTO before returning
                            return ResponseEntity.ok(new CompanyDto(updatedCompany));
                        })
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .orElseThrow(() -> new InvalidCompanyException("Company not found"));
    }


    @GetMapping("/user/{email}")
    public ResponseEntity<List<CompanyDto>> getCompaniesByUserId(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    List<CompanyDto> companyDtos = companyRepository.findByUserId(user.getId())
                            .stream()
                            .map(CompanyDto::new)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(companyDtos);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}