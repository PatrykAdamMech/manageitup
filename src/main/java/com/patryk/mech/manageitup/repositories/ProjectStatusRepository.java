package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.ProjectStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectStatusRepository extends CrudRepository<ProjectStatus, UUID> {
}
