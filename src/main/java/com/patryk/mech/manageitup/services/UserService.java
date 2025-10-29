package com.patryk.mech.manageitup.services;

import ch.qos.logback.core.util.StringUtil;
import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.UserCreateRequest;
import com.patryk.mech.manageitup.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User saveUserFromRequest(UserCreateRequest ucr) {
        if(ucr.getId() != null) {
            User existing = userRepository.findById(ucr.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found: " + ucr.getId()));
            existing.setUsername(ucr.getUsername());

            if(!StringUtil.isNullOrEmpty(ucr.getPassword())) {
                existing.setPassword(encoder.encode(ucr.getPassword()));
            }

            existing.setEmail(ucr.getEmail());
            existing.setName(ucr.getName());
            existing.setLastName(ucr.getLastName());
            existing.setRole(ucr.getRole());

            return userRepository.save(existing);
        }

        ucr.setPassword(encoder.encode(ucr.getPassword()));
        return userRepository.save(ucr.asUser());
    }

}
