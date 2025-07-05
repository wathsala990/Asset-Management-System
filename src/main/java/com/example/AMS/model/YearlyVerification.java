package com.example.AMS.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class YearlyVerification {

    @Id
    private String verificationID;

    @ManyToOne
    @JoinColumn(name = "assetID")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    private Date verificationDate;
    private String verificationDetails;
    private Boolean verification;

    // Getters and Setters

    public String getVerificationID() {
        return verificationID;
    }

    public void setVerificationID(String verificationID) {
        this.verificationID = verificationID;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(Date verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getVerificationDetails() {
        return verificationDetails;
    }

    public void setVerificationDetails(String verificationDetails) {
        this.verificationDetails = verificationDetails;
    }

    public Boolean getVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }
}
