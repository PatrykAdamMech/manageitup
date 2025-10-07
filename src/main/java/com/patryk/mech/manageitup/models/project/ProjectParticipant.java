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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private UUID projectID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Users", referencedColumnName = "id")
    private User user;
    private ProjectRoles role;

    public ProjectParticipant() {}

    public ProjectParticipant(UUID projectID, ProjectRoles role, User userID) {
        this.projectID = projectID;
        this.role = role;
        this.user = userID;
    }
}
