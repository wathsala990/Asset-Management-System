package com.example.AMS.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Condemn {

    @Id
    private String condemnID;

    @ManyToOne
    @JoinColumn(name = "assetID")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "locationID")
    private Location location;

    private Date condemnDate;
    private String condemnDetails;
    private Boolean activityStates;

    // Getters and Setters

    public String getCondemnID() {
        return condemnID;
    }

    public void setCondemnID(String condemnID) {
        this.condemnID = condemnID;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getCondemnDate() {
        return condemnDate;
    }

    public void setCondemnDate(Date condemnDate) {
        this.condemnDate = condemnDate;
    }

    public String getCondemnDetails() {
        return condemnDetails;
    }

    public void setCondemnDetails(String condemnDetails) {
        this.condemnDetails = condemnDetails;
    }

    public Boolean getActivityStates() {
        return activityStates;
    }

    public void setActivityStates(Boolean activityStates) {
        this.activityStates = activityStates;
    }
}
