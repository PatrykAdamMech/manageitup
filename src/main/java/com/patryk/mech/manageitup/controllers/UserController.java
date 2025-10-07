package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        if(user != null) {
            userRepository.save(user);
            return new ResponseEntity<String>("OK!", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Bad Request!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserById(@RequestParam UUID id) {
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
