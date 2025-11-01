package com.patryk.mech.manageitup.repositories;

import com.patryk.mech.manageitup.models.project.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends CrudRepository<Task, UUID> {

    final String GRAPH_ALL = "Task.withAll";

    @EntityGraph(GRAPH_ALL)
    List<Task> findByProject_Id(UUID projectId);
}
