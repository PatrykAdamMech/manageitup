package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.UserRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProjectParticipantCreateRequest {
    private UUID userId;
    private ProjectParticipant.ProjectRoles role;
    private List<UUID> projectIds;

    public ProjectParticipantCreateRequest() {
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public ProjectParticipant.ProjectRoles getRole() {
        return role;
    }

    public void setRole(ProjectParticipant.ProjectRoles role) {
        this.role = role;
    }

    public List<UUID> getProjectIds() {
        return projectIds;
    }

    public void setProjectIds(List<UUID> projectIds) {
        this.projectIds = projectIds;
    }

    public ProjectParticipant asProjectParticipant(EntityManager em) {
        ProjectParticipant pp = new ProjectParticipant();

        pp.setRole(this.role);

        User user = em.getReference(User.class, this.getUserId());
        if(Objects.nonNull(user)) {
            pp.setUser(user);
        }

        return pp;
    }
}
