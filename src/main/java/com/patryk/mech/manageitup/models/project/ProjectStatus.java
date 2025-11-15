package com.patryk.mech.manageitup.models.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.services.WorkflowService;
import com.patryk.mech.manageitup.shared.NameUtils;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="ProjectStatuses")
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private int priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    @JsonIgnore
    @Nullable
    private Workflow workflow;

    public ProjectStatus() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = NameUtils.capitalizeWords(name);
    }

    @Nullable
    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(@Nullable Workflow workflow) {
        this.workflow = workflow;
    }

    @Override
    public String toString() {
        return "ProjectStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
