package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.repositories.ProjectStatusRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkflowCreateRequest {

    private String name;
    private List<UUID> statuses;

    public WorkflowCreateRequest() {
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

        w.setName(this.getName());
        List<ProjectStatus> s = new ArrayList<>();
        for(UUID ps : statuses) {
            ProjectStatus status = em.getReference(ProjectStatus.class, ps);
            s.add(status);
        }
        w.setStatuses(s);
        return w;
    }
}
