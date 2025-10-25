package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private String name;
    private String lastName;
    private User.UserRoles role;
    private LocalDateTime createdOn;
    private LocalDateTime lastModified;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setRole(user.getRole());
        this.setLastName(user.getLastName());
        this.setCreatedOn(user.getCreatedOn());
        this.setLastModified(user.getLastModified());
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public User.UserRoles getRole() {
        return role;
    }

    public void setRole(User.UserRoles role) {
        this.role = role;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public static UserResponse fromUser(User user) {
        return user == null ? null : new UserResponse(user);
    }
}
