package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkflowCreateRequest {

    private UUID id;
    private String name;
    private List<UUID> statuses;

    public WorkflowCreateRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<UUID> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<UUID> statuses) {
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Workflow asWorkflow(EntityManager em) {
        Workflow w = new Workflow();

        w.setId(this.getId());
        w.setName(this.getName());
        List<ProjectStatus> s = new ArrayList<>();
        for(UUID ps : statuses) {
            ProjectStatus status = em.getReference(ProjectStatus.class, ps);
            s.add(status);
        }
        w.setStatus(s);
        return w;
    }
}
