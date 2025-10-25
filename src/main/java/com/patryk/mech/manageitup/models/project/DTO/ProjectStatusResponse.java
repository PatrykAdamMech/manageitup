package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.project.ProjectStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectStatusResponse {

    private UUID id;
    private String name;
    private int priority;

    public ProjectStatusResponse() {
    }

    public ProjectStatusResponse(ProjectStatus status) {
        this.setId(status.getId());
        this.setName(status.getName());
        this.setPriority(status.getPriority());
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static ProjectStatusResponse fromStatus(ProjectStatus status) {
        return status == null ? null : new ProjectStatusResponse(status);
    }

    public static List<ProjectStatusResponse> fromStatusList(List<ProjectStatus> statuses) {
        if(statuses == null) return null;
        List<ProjectStatusResponse> response = new ArrayList<>();
        for(ProjectStatus s : statuses) {
            response.add(fromStatus(s));
        }

        return response;
    }
}
