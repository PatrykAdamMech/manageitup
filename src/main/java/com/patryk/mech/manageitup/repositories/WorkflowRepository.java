package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowOptionProjection;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkflowRepository extends CrudRepository<Workflow, UUID> {
    Page<WorkflowOptionProjection> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<WorkflowOptionProjection> findAllBy(Pageable pageable);

    Optional<Workflow> findById(@NonNull UUID id);
}
