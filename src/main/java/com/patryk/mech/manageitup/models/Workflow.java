package com.patryk.mech.manageitup.models;

import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import com.patryk.mech.manageitup.models.project.ProjectStatus;
import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name="Workflows")
public class Workflow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int priority;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="ProjectStatuses", referencedColumnName = "id")
    private ProjectParticipant status;

    Workflow() {}
}
