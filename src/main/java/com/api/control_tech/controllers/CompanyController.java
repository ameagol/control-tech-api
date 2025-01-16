package com.api.control_tech.controllers;

import com.api.control_tech.exceptions.DeviceNotFoundException;
import com.api.control_tech.exceptions.InvalidCompanyException;
import com.api.control_tech.exceptions.UserNotFoundException;
import com.api.control_tech.models.CompanyDto;
import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.repositories.CompanyRepository;
import com.api.control_tech.persistence.repositories.UserRepository;
import com.api.control_tech.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private AuthService authService;
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
                            Optional.of(companyRepository.existsByNameAndUser(c.getName(), user))
                                    .filter(exists -> !exists)
                                    .orElseThrow(InvalidCompanyException::new);
                            return companyRepository.save(new Company(c.getName(), user));
                        }))
                .map(ResponseEntity::ok)
                .orElseThrow(InvalidCompanyException::new);

    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody CompanyDto companyDto) {
        return companyRepository.findById(companyDto.getId())
                .map(existingCompany -> userRepository.findByEmail(companyDto.getUser().getEmail())
                        .map(user -> {
                            existingCompany.setName(companyDto.getName());
                            existingCompany.setUser(user);
                            Company updatedCompany = companyRepository.save(existingCompany);
                            return ResponseEntity.ok(new CompanyDto(updatedCompany));
                        })
                        .orElseThrow(UserNotFoundException::new))
                .orElseThrow(InvalidCompanyException::new);
    }


    @GetMapping
    public ResponseEntity<List<CompanyDto>> getCompaniesByUserId(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");

        String userEmail = Optional.ofNullable(authService.getUserEmailFromToken(token))
                .orElseThrow(DeviceNotFoundException::new);

        return userRepository.findByEmail(userEmail)
                .map(user -> {
                    List<CompanyDto> companyDtos = companyRepository.findByUserId(user.getId())
                            .stream()
                            .map(CompanyDto::new)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(companyDtos);
                })
                .orElseThrow(UserNotFoundException::new);
    }
}