package com.example.AMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
public class Maintain {
    @Id
    private String maintainID;

    @ManyToOne
    @JoinColumn(name = "assetID")
    private Asset asset;

    public Date getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(Date maintainDate) {
        this.maintainDate = maintainDate;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getMaintainID() {
        return maintainID;
    }

    public void setMaintainID(String maintainID) {
        this.maintainID = maintainID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    private Date maintainDate;
    private String description;
    private Float cost;
}
