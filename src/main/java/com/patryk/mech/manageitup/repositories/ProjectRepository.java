package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Project;
import org.springframework.core.env.PropertyResolverExtensionsKt;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {

    final String GRAPH_ALL = "Project.withAll";

    @EntityGraph(GRAPH_ALL)
    List<Project> findAll();

    @EntityGraph(GRAPH_ALL)
    Optional<Project> findById(UUID id);

    @EntityGraph(GRAPH_ALL)
    List<Project> findDistinctByOwner_Id(UUID userId);

    @EntityGraph(GRAPH_ALL)
    List<Project> findDistinctByParticipants_User_Id(UUID userId);


}
