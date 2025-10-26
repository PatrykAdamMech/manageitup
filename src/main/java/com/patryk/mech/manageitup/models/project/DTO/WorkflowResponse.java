package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.ProjectStatus;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class WorkflowResponse {

    private UUID id;
    private String name;
    private List<ProjectStatusResponse> statuses;

    public WorkflowResponse() {
    }

    public WorkflowResponse(Workflow workflow) {
        this.setId(workflow.getId());
        this.setName(workflow.getName());
        this.setStatuses(ProjectStatusResponse
                .fromStatusList(workflow.getStatuses()));
        this.statuses.sort(Comparator.comparingInt(ProjectStatusResponse::getPriority));
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

    public List<ProjectStatusResponse> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ProjectStatusResponse> statuses) {
        this.statuses = statuses;
    }

    public void addStatus(ProjectStatusResponse status) {
        if(status == null) return;
        this.statuses.add(status);
    }

    public void removeStatus(ProjectStatusResponse status) {
        if(status == null) return;
        this.statuses.remove(status);
    }

    public static WorkflowResponse fromWorkflow(Workflow workflow) {
        return workflow == null ? null : new WorkflowResponse(workflow);
    }


}
