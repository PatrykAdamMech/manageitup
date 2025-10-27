package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.UserCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.UserOptionProjection;
import com.patryk.mech.manageitup.models.project.DTO.UserResponse;
import com.patryk.mech.manageitup.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {

        Iterable<User> users = userRepository.findAll();

        return StreamSupport
                .stream(users.spliterator(), false)
                .map(UserResponse::fromUser)
                .toList();
    }

    @GetMapping("/all/{id}")
    public User getAllUsers(@PathVariable("id") UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/select")
    public List<GenericOptionsResponse> select(
            @RequestParam(required = false) String matcher,
            @RequestParam(defaultValue = "10") int limit
    ) {
        var pageReq = PageRequest.of(0, Math.min(Math.max(limit, 1), 50),
                Sort.by("name").ascending().and(Sort.by("lastName").ascending()));

        Page<UserOptionProjection> page = (matcher == null || matcher.isBlank())
                ? userRepository.findAllOptionsBy(pageReq)
                : userRepository
                .findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        matcher.trim(), matcher.trim(), matcher.trim(), matcher.trim(), pageReq);

        return page.getContent().stream()
                .map(u -> new GenericOptionsResponse(u.getId(), buildLabel(u.getName(), u.getLastName(), u.getUsername())))
                .toList();
    }

    private static String buildLabel(String name, String lastName, String username) {
        String n = name == null ? "" : name.trim();
        String l = lastName == null ? "" : lastName.trim();
        String full = (n + " " + l).trim();
        return full.isEmpty() ? (username == null ? "" : username) : full;
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveUser(@RequestBody UserCreateRequest user) {
        if(user != null) {
            user.setPassword(encoder.encode(user.getPassword()));

            UUID savedId = userRepository.save(user.asUser()).getId();

            return new ResponseEntity<String>(savedId.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable UUID id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if(foundUser != null) {
            userRepository.delete(foundUser);
            return new ResponseEntity<String>("OK!", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Not Found!", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        User foundUser = userRepository.findById(user.getId()).orElse(null);
        if(foundUser != null) {
            userRepository.save(user);
            return new ResponseEntity<String>("OK!", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Not Found!", HttpStatus.NOT_FOUND);
    }
}
