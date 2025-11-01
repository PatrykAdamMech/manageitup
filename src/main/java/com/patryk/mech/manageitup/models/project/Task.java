package com.patryk.mech.manageitup.models.project;

import jakarta.persistence.*;

import java.util.UUID;

@NamedEntityGraph(
        name = "Task.withAll",
        attributeNodes = {
                @NamedAttributeNode("assignee"),
                @NamedAttributeNode("project")
        }
)
@Entity
@Table(name="Tasks")
public class Task {

    public enum TaskStatus {

        NOT_ASSIGNED("Not assigned",0),
        ASSIGNED("Assigned", 25),
        IN_PROGRESS("In progress", 50),
        CLOSED("Closed",100);

        private final String label;
        private final int progress;

        TaskStatus(String label, int progress) {
            this.label = label;
            this.progress = progress;
        }

        public String getLabel() {
            return this.label;
        }

        public int getProgress() {
            return this.progress;
        }
    }

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", referencedColumnName = "id")
    private ProjectParticipant assignee;

    private TaskStatus status;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Task() {
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

    public ProjectParticipant getAssignee() {
        return assignee;
    }

    public void setAssignee(ProjectParticipant assignee) {
        this.assignee = assignee;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", assignee=" + assignee +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
