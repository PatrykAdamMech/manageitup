package com.patryk.mech.manageitup.controllers;

import com.patryk.mech.manageitup.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authManager, JwtService jwtService) {
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

//    @PostMapping("/login")
//    public Map<String, String> login(@RequestBody LoginRequest req) {
//        Authentication auth = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
//        UserDetails user = (UserDetails) auth.getPrincipal();
//        String token = jwtService.generateToken(user); // include roles if you want
//        return Map.of("token", token);
//    }

    public record LoginRequest(String email, String password) {}
}
