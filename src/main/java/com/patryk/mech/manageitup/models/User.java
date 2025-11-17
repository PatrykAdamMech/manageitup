package com.patryk.mech.manageitup.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.patryk.mech.manageitup.models.project.Project;
import com.patryk.mech.manageitup.models.project.ProjectParticipant;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="Users")
@NamedEntityGraph(
        name = "User.withRelations",
        attributeNodes = {
                @NamedAttributeNode(value = "participantList", subgraph = "participant-subgraph"),
                @NamedAttributeNode(value = "ownedProjects", subgraph = "projects-subgraph")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "participant-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("project")
                        }
                ),
                @NamedSubgraph(
                        name = "projects-subgraph",
                        attributeNodes = {
                                @NamedAttributeNode("workflow"),
                                @NamedAttributeNode("status")
                        }
                )
        }
)
public class User {

    public enum UserRoles {
        SYSTEM_ADMIN("SYSTEM_ADMIN"),
        PMO("PMO"),
        USER("USER");

        private final String name;

        UserRoles(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public String getName() {
            return this.name;
        }
    }

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private UserRoles role;

    @CreationTimestamp
    @Column
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column
    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    @Nullable
    private List<ProjectParticipant> participantList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnore
    @Nullable
    private List<Project> ownedProjects;

    public User() {}

    public User(String username, String password, String email, UserRoles role, String name, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public User(User user) {
        this(user.getUsername(), user.getPassword(), user.getEmail(), user.getRole(), user.getName(), user.getLastName());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    @Nullable
    public List<ProjectParticipant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(@Nullable List<ProjectParticipant> participantList) {
        this.participantList = participantList;
    }

    @Nullable
    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(@Nullable List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createdOn=" + createdOn +
                ", lastModified=" + lastModified +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return this.id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
