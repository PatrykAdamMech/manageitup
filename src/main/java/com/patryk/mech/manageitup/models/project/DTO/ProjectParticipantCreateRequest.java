package com.patryk.mech.manageitup.models.project.DTO;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.repositories.ProjectRepository;
import com.patryk.mech.manageitup.repositories.UserRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.Null;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProjectParticipantCreateRequest {
    private UUID userId;
    private ProjectParticipant.ProjectRoles role;
    @Nullable
    private UUID projectId;

    public ProjectParticipantCreateRequest() {
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    // debug
    @JsonSetter("userId")
    public void setUserIdFromString(Object value) {
        if (value == null) {
            this.userId = null;
            return;
        }
        if (value instanceof UUID) {
            this.userId = (UUID) value;
            return;
        }
        try {
            this.userId = UUID.fromString(value.toString());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid userId: " + value, ex);
        }
    }

    public ProjectParticipant.ProjectRoles getRole() {
        return role;
    }

    public void setRole(ProjectParticipant.ProjectRoles role) {
        this.role = role;
    }

    @Nullable
    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nullable UUID projectId) {
        this.projectId = projectId;
    }

    @Transactional
    public ProjectParticipant asProjectParticipant(EntityManager em) {
        ProjectParticipant pp = new ProjectParticipant();

        pp.setRole(this.getRole());

        User user = em.getReference(User.class, this.getUserId());
        if(Objects.nonNull(user)) {
            pp.setUserId(user);
        }

        if(Objects.nonNull(this.getProjectId())) {
            Project project = em.getReference(Project.class, this.getProjectId());
            if(Objects.nonNull(project)) {
                pp.setProject(project);
            }
        }

        return pp;
    }

    @Override
    public String toString() {
        return "ProjectParticipantCreateRequest{" +
                "userId=" + userId +
                ", role=" + role +
                ", projectId=" + projectId +
                '}';
    }
}
