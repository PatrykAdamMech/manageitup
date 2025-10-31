package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.login.LoginRequest;
import com.patryk.mech.manageitup.models.login.LoginResult;
import com.patryk.mech.manageitup.repositories.UserRepository;
import com.patryk.mech.manageitup.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
//@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
@Tag(name = "User Login", description = "APIs for authorizing users")
public class LoginController {

    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public LoginController(UserRepository users, PasswordEncoder encoder, AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.users = users;
        this.encoder = encoder;
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            String token = tokenProvider.generateToken(auth);
            User user = users.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found!: " + loginRequest.getEmail()));
            return ResponseEntity.ok(new LoginResult(token, user.getRole(), user.getId()));
        } catch (org.springframework.security.core.AuthenticationException ex) {
            System.out.println("Login failed for " + loginRequest.getEmail() + ": " + ex.getClass().getSimpleName() + " - " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
