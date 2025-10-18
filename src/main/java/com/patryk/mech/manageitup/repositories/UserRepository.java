package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.DTO.UserOptionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {


    Optional<User> findByEmail(String email);

    Page<UserOptionProjection> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String username, String name, String lastName, String email, Pageable pageable);

    Page<UserOptionProjection> findAllBy(Pageable pageable);
}
