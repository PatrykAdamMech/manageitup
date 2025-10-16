package com.patryk.mech.manageitup.models;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import jakarta.persistence.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name="Workflows")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToMany
    @JoinTable(
            name = "workflow_statuses",
            joinColumns = @JoinColumn(name = "workflow_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id")
    )
    private List<ProjectStatus> statuses;

    public Workflow() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public boolean addStatus(ProjectStatus ps) {
        return this.statuses.add(ps);
    }
}
