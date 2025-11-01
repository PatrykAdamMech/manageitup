package com.patryk.mech.manageitup.models.project;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.Workflow;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name="Projects")
@NamedEntityGraph(
        name = "Project.withAll",
        attributeNodes = {
                @NamedAttributeNode("owner"),
                @NamedAttributeNode("workflow"),
                @NamedAttributeNode("status"),
                @NamedAttributeNode(value = "participants", subgraph = "participants-subgraph"),
                @NamedAttributeNode(value = "tasks", subgraph = "tasks-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "participants-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("user")
                        }
                ),
                @NamedSubgraph(
                        name = "tasks-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("assignee"),
                                @NamedAttributeNode("project")
                        }
                )
        }
)
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_id")
    private Workflow workflow;

    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<ProjectParticipant> participants;

    @CreationTimestamp
    @Column
    private LocalDateTime creationTimeStamp;

    @UpdateTimestamp
    @Column
    private LocalDateTime lastUpdatedTimestamp;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ProjectStatuses", referencedColumnName = "id")
    private ProjectStatus status;


    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Task> tasks;

    public Project() {
        this.participants = new ArrayList<>();
        this.creationTimeStamp = LocalDateTime.now();
        this.lastUpdatedTimestamp = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getLastUpdatedTimestamp() {
        return lastUpdatedTimestamp;
    }

    public void setLastUpdatedTimestamp(LocalDateTime lastUpdatedTimestamp) {
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }

    public LocalDateTime getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(LocalDateTime creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }

    public List<ProjectParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ProjectParticipant> participants) {
        this.participants = participants;
    }

    public boolean addParticipant(ProjectParticipant participant) {
        return this.participants.add(participant);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
