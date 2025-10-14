package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.login.LoginRequest;
import com.patryk.mech.manageitup.models.login.LoginResult;
import com.patryk.mech.manageitup.repositories.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8081"})
@Tag(name = "User Login", description = "APIs for authorizing users")
public class LoginController {

    private final UserRepository users;
    private final PasswordEncoder encoder;

    public LoginController(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping(value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResult> login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = users.findByEmail(loginRequest.getEmail());

        if(user.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResult(false, LoginResult.Result.NOT_REGISTERED));

        if(!encoder.matches(loginRequest.getPassword(), user.get().getPassword())) {
            return new ResponseEntity<LoginResult>(new LoginResult(false, LoginResult.Result.WRONG_PASSWORD), HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResult(true, LoginResult.Result.SUCCESSFUL));
    }

}
