package com.patryk.mech.manageitup.models.project;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="ProjectStatuses")
public class ProjectStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    ProjectStatus() {}
}
