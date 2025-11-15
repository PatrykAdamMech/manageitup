package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.User;
import com.patryk.mech.manageitup.shared.DateUtils;

import java.util.UUID;

public class UserResponse {

    private UUID id;
    private String username;
    private String email;
    private String name;
    private String lastName;
    private User.UserRoles role;
    private String createdOn;
    private String lastModified;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setName(user.getName());
        this.setRole(user.getRole());
        this.setLastName(user.getLastName());
        this.setCreatedOn(DateUtils.toLocalTimeString(user.getCreatedOn()));
        this.setLastModified(DateUtils.toLocalTimeString(user.getLastModified()));
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public static UserResponse fromUser(User user) {
        return user == null ? null : new UserResponse(user);
    }
}
