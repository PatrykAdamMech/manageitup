package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import org.springframework.data.repository.CrudRepository;

public interface ProjectParticipantRepository extends CrudRepository<ProjectParticipant, Integer> {
}
