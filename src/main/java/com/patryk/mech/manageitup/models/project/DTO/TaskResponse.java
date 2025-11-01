package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.project.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskResponse {

    private UUID id;
    private String name;
    private ProjectParticipantResponse assignee;
    private Task.TaskStatus status;
    private String description;
    private UUID projectId;

    public TaskResponse() {
    }

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.status = task.getStatus();
        this.assignee = ProjectParticipantResponse.fromProjectParticipant(task.getAssignee());
        this.description = task.getDescription();
        this.projectId = task.getProject().getId();
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

    public ProjectParticipantResponse getAssignee() {
        return assignee;
    }

    public void setAssignee(ProjectParticipantResponse assignee) {
        this.assignee = assignee;
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

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public static TaskResponse fromTask(Task task) {
        return task == null ? null : new TaskResponse(task);
    }

    public static List<TaskResponse> fromTaskList(List<Task> tasks) {
        if(tasks == null) return null;
        List<TaskResponse> responses = new ArrayList<>();
        for(Task task: tasks) {
            responses.add(fromTask(task));
        }
        return responses;
    }
}
