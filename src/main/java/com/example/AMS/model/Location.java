package com.example.AMS.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Date;
import java.util.List;

@Entity
public class Location {
    @Id
    private String locationID;

    private String roomNo;
    private String roomName;
    private Date startDate;
    private Date endDate;

    @OneToMany(mappedBy = "location")
    private List<Asset> assets;

    @OneToMany(mappedBy = "location")
    private List<Transfer> transfers;

    @OneToMany(mappedBy = "location")
    private List<Condemn> condemns;

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public List<Transfer> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<Transfer> transfers) {
        this.transfers = transfers;
    }

    public List<Condemn> getCondemns() {
        return condemns;
    }

    public void setCondemns(List<Condemn> condemns) {
        this.condemns = condemns;
    }
}
