package com.patryk.mech.manageitup.models.project.DTO;

import com.fasterxml.jackson.databind.JsonNode;
import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.DTO.WorkflowCreateRequest;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProjectCreateFullRequest {

    private UUID id;
    private String title;
    private UUID ownerId;
    private WorkflowCreateRequest workflow;
    private List<ProjectParticipantCreateRequest> participants;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID statusId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public WorkflowCreateRequest getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowCreateRequest workflow) {
        this.workflow = workflow;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public List<ProjectParticipantCreateRequest> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ProjectParticipantCreateRequest> participants) {
        this.participants = participants;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID status) {
        this.statusId = status;
    }

    public ProjectCreateFullRequest() {}

    @Override
    public String toString() {
        return "ProjectCreateFullRequest{" +
                "title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", workflow=" + workflow +
                ", participants=" + participants +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", statusId=" + statusId +
                '}';
    }
}
