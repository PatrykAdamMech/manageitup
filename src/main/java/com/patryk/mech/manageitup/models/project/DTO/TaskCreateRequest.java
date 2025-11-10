package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class TaskCreateRequest {

    private UUID id;
    private String name;
    private UUID assigneeId;
    private Task.TaskStatus status;
    private String description;
    private UUID projectId;

    public TaskCreateRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task.TaskStatus getStatus() {
        return status;
    }

    public void setStatus(Task.TaskStatus status) {
        this.status = status;
    }

    public UUID getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(UUID assigneeId) {
        this.assigneeId = assigneeId;
    }

    @Override
    public String toString() {
        return "TaskCreateRequest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assigneeId=" + assigneeId +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", projectId=" + projectId +
                '}';
    }
}
