package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.DTO.ProjectStatusOptionProjection;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectStatusRepository extends CrudRepository<ProjectStatus, UUID> {

    Page<ProjectStatusOptionProjection> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<ProjectStatusOptionProjection> findAllBy(Pageable pageable);

    Optional<ProjectStatus> findById(@NonNull UUID id);
}
