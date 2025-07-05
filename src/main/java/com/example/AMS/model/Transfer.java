package com.example.AMS.model;

import jakarta.persistence.*;
import java.util.Date; // ✅ This import is required

@Entity
public class Transfer {

    @Id
    private String transferID;

    @ManyToOne
    @JoinColumn(name = "assetID")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "locationID")
    private Location location;

    private Date transferDate;
    private String departmentName;

    // Getters and Setters

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
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

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
