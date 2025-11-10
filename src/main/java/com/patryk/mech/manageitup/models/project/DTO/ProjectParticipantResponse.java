package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class ProjectParticipantResponse {

    private UUID id;
    private UserResponse user;
    private ProjectParticipant.ProjectRoles role;
    private UUID projectId;

    public ProjectParticipantResponse() {
    }

    public ProjectParticipantResponse(ProjectParticipant participant) {
        this.setId(participant.getId());
        this.setUser(participant.getUser() == null ? null : UserResponse.fromUser(participant.getUser()));
        this.setRole(participant.getRole());
        this.setProjectId(participant.getProject() == null ? null : participant.getProject().getId());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public ProjectParticipant.ProjectRoles getRole() {
        return role;
    }

    public void setRole(ProjectParticipant.ProjectRoles role) {
        this.role = role;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    @Transactional
    public static ProjectParticipantResponse fromProjectParticipant(ProjectParticipant participant) {
        return participant == null ? null : new ProjectParticipantResponse(participant);
    }

    public static Set<ProjectParticipantResponse> fromProjectParticipantList(Set<ProjectParticipant> participants) {
        if(participants == null) return null;
        Set<ProjectParticipantResponse> responses = new HashSet<>();
        for(ProjectParticipant pp : participants) {
            responses.add(fromProjectParticipant(pp));
        }

        return responses;
    }
}
