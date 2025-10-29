package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.common.GenericOptionsResponse;
import com.patryk.mech.manageitup.models.project.DTO.UserCreateRequest;
import com.patryk.mech.manageitup.models.project.DTO.UserOptionProjection;
import com.patryk.mech.manageitup.models.project.DTO.UserResponse;
import com.patryk.mech.manageitup.repositories.UserRepository;
import com.patryk.mech.manageitup.services.UserService;
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

    private PasswordEncoder encoder;

    private UserRepository userRepository;

    private UserService service;

    public UserController(UserService service, UserRepository userRepository, PasswordEncoder encoder) {
        this.service = service;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @GetMapping("/all")
    public List<UserResponse> getAllUsers() {

        Iterable<User> users = userRepository.findAll();

        List<UserResponse> responses = StreamSupport
                .stream(users.spliterator(), false)
                .map(UserResponse::fromUser)
                .toList();
        return responses;
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

            UUID savedId = userRepository.save(user.asUser()).getId();

            return new ResponseEntity<String>(savedId.toString(), HttpStatus.OK);
        }

        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserCreateRequest user) {
        if(user != null) {
            if(Objects.isNull(user.getId()))
                return new ResponseEntity<String>("Missing ID!", HttpStatus.BAD_REQUEST);

            UUID savedId = service.saveUserFromRequest(user).getId();

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
}
