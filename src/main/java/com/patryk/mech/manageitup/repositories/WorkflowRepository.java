package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.Workflow;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WorkflowRepository extends CrudRepository<Workflow, UUID> {
}
