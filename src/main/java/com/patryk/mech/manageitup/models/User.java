package com.patryk.mech.manageitup.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

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
    private String email;
    private String name;
    private String lastName;
    @CreationTimestamp
    @Column
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column
    private LocalDateTime lastModified;

    public User() {}

    public User(String username, String password, String email, String name, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.createdOn = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public User(User user) {
        this(user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getLastName());
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
}
