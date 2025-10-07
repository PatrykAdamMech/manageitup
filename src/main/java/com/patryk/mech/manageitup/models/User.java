package com.patryk.mech.manageitup.models;

import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    private String username;
    private String password;
    private String name;
    private String lastName;
    private LocalDateTime createdOn;
    private LocalDateTime lastModified;

    public User() {
        this("null","null","null","null");
    }

    public User(String username, String password, String name, String lastName) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public User(User user) {
        this(user.getUsername(), user.getPassword(), user.getName(), user.getLastName());
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


}
