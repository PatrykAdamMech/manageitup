package com.patryk.mech.manageitup.models.project;

import com.patryk.mech.manageitup.models.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="ProjectParticipants")
public final class ProjectParticipant {

    public enum ProjectRoles {
        DEVELOPER("Developer"),
        TESTER("Tester"),
        PROJECT_MANAGER("Project Manager"),
        SALES_EXECUTIVE("Sales Executive"),
        PMO_RESPONSIBLE("PMO Responsible");

        private final String title;

        ProjectRoles(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Users", referencedColumnName = "id")
    private User user;
    private ProjectRoles role;

    private UUID projectID;

    public ProjectParticipant() {}

    public ProjectParticipant(ProjectRoles role, User userID) {
        this.role = role;
        this.user = userID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ProjectRoles getRole() {
        return role;
    }

    public void setRole(ProjectRoles role) {
        this.role = role;
    }

    public UUID getProjectID() {
        return projectID;
    }

    public void setProjectID(UUID projectID) {
        this.projectID = projectID;
    }
}
