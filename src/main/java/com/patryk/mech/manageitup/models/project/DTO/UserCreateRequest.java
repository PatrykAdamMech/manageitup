package com.patryk.mech.manageitup.models.project.DTO;

import com.patryk.mech.manageitup.models.User;

public class UserCreateRequest {

    private String username;
    private String password;
    private String email;
    private String name;
    private String lastName;
    private User.UserRoles role;

    public UserCreateRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User.UserRoles getRole() {
        return role;
    }

    public void setRole(User.UserRoles role) {
        this.role = role;
    }

    public User asUser() {
        User finalUser = new User();

        finalUser.setUsername(this.username);
        finalUser.setPassword(this.password);
        finalUser.setEmail(this.email);
        finalUser.setName(this.name);
        finalUser.setLastName(this.lastName);
        finalUser.setRole(this.role);

        return finalUser;

    }
}
