package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WorkflowCreateRequest {

    @Nullable
    private UUID id;
    private String name;
    private List<ProjectStatus> statuses;

    public WorkflowCreateRequest() {
    }

    public List<ProjectStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ProjectStatus> statuses) {
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public UUID getId() {
        return id;
    }

    public void setId(@Nullable UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WorkflowCreateRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", statuses=" + statuses +
                '}';
    }
}
