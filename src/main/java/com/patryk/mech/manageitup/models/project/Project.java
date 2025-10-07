package com.patryk.mech.manageitup.models.project;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.models.Workflow;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Projects")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Users", referencedColumnName = "id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="Workflows", referencedColumnName = "id")
    private Workflow workflow;

    @OneToMany(mappedBy = "projectID")
    private List<ProjectParticipant> participants;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Project() {
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
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
}
