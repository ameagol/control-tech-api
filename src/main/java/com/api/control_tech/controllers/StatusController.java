package com.api.control_tech.controllers;

import com.api.control_tech.exceptions.StatusAlreadyExistsException;
import com.api.control_tech.exceptions.StatusNotFoundException;
import com.api.control_tech.models.StatusDto;
import com.api.control_tech.persistence.entities.Status;
import com.api.control_tech.persistence.repositories.StatusRepository;
import com.api.control_tech.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/status")
public class StatusController {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Status> createCompany(@RequestBody StatusDto company) {
        return Optional.of(company)
                .flatMap(c -> userRepository.findByEmail(c.getUser().getEmail())
                        .map(user -> {
                            if (statusRepository.existsByNameAndUser(c.getName(), user)) {
                                throw new StatusAlreadyExistsException("Status name already exists for this user.");
                            }
                            return statusRepository.save(new Status(c.getName(), user));
                        }))
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new StatusNotFoundException("Failed to Create Company"));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<StatusDto>> getAllStatuses(@PathVariable String username) {
        return userRepository.findByName(username)
                .map(user -> {
                    List<StatusDto> statusDtos = statusRepository.findByUserId(user.getId())
                            .stream()
                            .map(StatusDto::new)
                            .collect(Collectors.toList());
                    return ResponseEntity.ok(statusDtos);
                })
                .orElseThrow(() -> new StatusNotFoundException("User not found"));
    }

}
