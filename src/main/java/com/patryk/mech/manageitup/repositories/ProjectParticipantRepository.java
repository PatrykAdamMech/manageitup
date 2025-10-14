package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProjectParticipantRepository extends CrudRepository<ProjectParticipant, UUID> {
}
