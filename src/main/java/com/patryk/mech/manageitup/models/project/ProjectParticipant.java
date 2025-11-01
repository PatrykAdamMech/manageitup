package com.patryk.mech.manageitup.models.project;

import com.patryk.mech.manageitup.models.User;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="ProjectParticipants", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id"}))
@NamedEntityGraph(
        name = "ProjectParticipant.userAndProject",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("project")
        }
)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private ProjectRoles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @OneToMany(mappedBy = "assignee",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Task> tasks;

    public ProjectParticipant() {}

    public ProjectParticipant(ProjectRoles role, User userID, Project project) {
        this.role = role;
        this.user = userID;
        this.project = project;
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

    public void setUserId(User user) {
        this.user = user;
    }

    public ProjectRoles getRole() {
        return role;
    }

    public void setRole(ProjectRoles role) {
        this.role = role;
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


}
