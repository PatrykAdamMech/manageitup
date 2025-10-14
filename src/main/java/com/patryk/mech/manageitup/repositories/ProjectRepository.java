package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID> {

}
