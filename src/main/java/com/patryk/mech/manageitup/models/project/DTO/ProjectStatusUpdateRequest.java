package com.patryk.mech.manageitup.models.project.DTO;

import java.util.UUID;

public class ProjectStatusUpdateRequest {
    private UUID projectId;
    private UUID statusId;

    public ProjectStatusUpdateRequest() {
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }
}
