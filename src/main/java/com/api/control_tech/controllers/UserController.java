package com.api.control_tech.controllers;

import com.api.control_tech.exceptions.UserAlreadyExistsException;
import com.api.control_tech.models.SubscribeDto;
import com.api.control_tech.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/subscribe")
    public ResponseEntity<SubscribeDto> registerUser(@RequestBody SubscribeDto subscribeDto) {
        return Optional.ofNullable(userService.registerUser(subscribeDto))
                .map(user -> ResponseEntity.ok(user))
                .orElseThrow(UserAlreadyExistsException::new);
    }
}
