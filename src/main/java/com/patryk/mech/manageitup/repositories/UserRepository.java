package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.UserOptionProjection;
import com.patryk.mech.manageitup.models.project.DTO.UserResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    Optional<User> findById(@NonNull UUID id);

    Page<UserOptionProjection> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String name, String lastName, String email, Pageable pageable);

    Page<UserOptionProjection> findAllOptionsBy(Pageable pageable);

    Page<User> findAll(Pageable pageable);
}
