package com.patryk.mech.manageitup.models;

import com.patryk.mech.manageitup.models.project.DTO.ProjectCreateFullRequest;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import com.patryk.mech.manageitup.shared.NameUtils;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name="Workflows")
@NamedEntityGraph(
        name = "Workflow.withStatuses",
        attributeNodes = {
                @NamedAttributeNode(value = "statuses", subgraph = "statuses-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "statuses-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("name"),
                                @NamedAttributeNode("priority")
                        }
                )
        }
)
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToMany(mappedBy = "workflow",
        cascade = CascadeType.ALL,
        orphanRemoval = true)
    private List<ProjectStatus> statuses = new ArrayList<>();

    public Workflow() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<ProjectStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<ProjectStatus> statuses) {
        this.statuses = statuses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = NameUtils.capitalizeWords(name);
    }

    public boolean addStatus(ProjectStatus ps) {

        ps.setWorkflow(this);
        return this.statuses.add(ps);
    }

    public boolean removeStatus(ProjectStatus ps) {
        ps.setWorkflow(null);
        return this.statuses.remove(ps);
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", statuses=" + statuses +
                '}';
    }
}
