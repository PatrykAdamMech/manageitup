package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}
