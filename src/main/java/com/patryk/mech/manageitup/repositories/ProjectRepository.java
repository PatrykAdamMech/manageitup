package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {

    @EntityGraph("Project.withAll")
    List<Project> findAll();

}
