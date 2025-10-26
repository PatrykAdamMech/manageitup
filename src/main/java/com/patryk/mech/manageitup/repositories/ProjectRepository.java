package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Project;
import org.springframework.core.env.PropertyResolverExtensionsKt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {

    @EntityGraph("Project.withAll")
    List<Project> findAll();

    @EntityGraph("Project.withAll")
    Optional<Project> findById(UUID id);

}
