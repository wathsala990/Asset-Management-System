package com.example.AMS.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    private String userID;

    @ManyToOne
    @JoinColumn(name = "assetID")
    private Asset asset;

    private String password;
    private String username;
    private String email;
    private String description;
    private String role;

    @OneToMany(mappedBy = "user")
    private List<YearlyVerification> verifications;

    // Getters and Setters

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<YearlyVerification> getVerifications() {
        return verifications;
    }

    public void setVerifications(List<YearlyVerification> verifications) {
        this.verifications = verifications;
    }
}
