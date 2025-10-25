package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.Workflow;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProjectCreateRequest {

    String title;
    List<UUID> participants;
    UUID workflowId;
    UUID ownerId;

    public ProjectCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(UUID workflowId) {
        this.workflowId = workflowId;
    }

    public List<UUID> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UUID> participants) {
        this.participants = participants;
    }

    public Project asProject(EntityManager em) {
        Project project = new Project();

        project.setTitle(this.title);

        System.out.println("asProject");

        if (Objects.nonNull(this.workflowId)) {
            Workflow workflow = em.getReference(Workflow.class, this.getWorkflowId());
            if (Objects.nonNull(workflow)) {
                project.setWorkflow(workflow);
            }
        }
        if (Objects.nonNull(this.ownerId)) {
            User user = em.getReference(User.class, this.getOwnerId());
            if (Objects.nonNull(user)) {
                project.setOwner(user);
            }
        }
        List<ProjectParticipant> parts = new ArrayList<>();

        if(Objects.nonNull(this.participants)) {
            for (UUID pp : this.getParticipants()) {
                ProjectParticipant participant = em.getReference(ProjectParticipant.class, pp);
                if (Objects.nonNull(participant)) {
                    parts.add(participant);
                }
            }
        }
        project.setParticipants(parts);

        return project;
    }
}
