package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ProjectResponse {

    private UUID id;
    private String title;
    private UserResponse owner;
    private WorkflowResponse workflow;
    private Set<ProjectParticipantResponse> participants;
    private LocalDateTime creationTimestamp;
    private LocalDateTime lastUpdatedTimestamp;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectStatusResponse currentStatus;
    private Set<TaskResponse> tasks;

    public ProjectResponse() {
    }

    public ProjectResponse(Project project) {
        this.setId(project.getId());
        this.setTitle(project.getTitle());
        this.setOwner(UserResponse.fromUser(project.getOwner()));
        this.setWorkflow(WorkflowResponse.fromWorkflow(project.getWorkflow()));
        this.setParticipants(ProjectParticipantResponse.fromProjectParticipantList(project.getParticipants()));
        this.setCreationTimestamp(project.getCreationTimeStamp());
        this.setLastUpdatedTimestamp(project.getLastUpdatedTimestamp());
        this.setStartDate(project.getStartDate());
        this.setEndDate(project.getEndDate());
        this.setCurrentStatus(ProjectStatusResponse.fromStatus(project.getStatus()));
        this.setTasks(TaskResponse.fromTaskList(project.getTasks()));
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProjectStatusResponse getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(ProjectStatusResponse currentStatus) {
        this.currentStatus = currentStatus;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public Set<ProjectParticipantResponse> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ProjectParticipantResponse> participants) {
        this.participants = participants;
    }

    public WorkflowResponse getWorkflow() {
        return workflow;
    }

    public void setWorkflow(WorkflowResponse workflow) {
        this.workflow = workflow;
    }

    public UserResponse getOwner() {
        return owner;
    }

    public void setOwner(UserResponse owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    public static ProjectResponse fromProject(Project project) {
        return project == null ? null : new ProjectResponse(project);
    }

    public static List<ProjectResponse> fromProjectList(List<Project> projects) {
        if(projects == null) return null;
        List<ProjectResponse> responses = new ArrayList<>();
        for(Project project : projects) {
            responses.add(fromProject(project));
        }
        return responses;
    }

}
