package com.patryk.mech.manageitup.models.project;

import com.patryk.mech.manageitup.models.Workflow;
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
        this.name = name;
    }
}
