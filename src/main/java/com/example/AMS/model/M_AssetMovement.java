// M_AssetMovement.java
package com.example.AMS.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class M_AssetMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;
    
    @ManyToOne
    @JoinColumn(name = "assetId")
    private Asset asset;
    
    @ManyToOne
    @JoinColumn(name = "fromLocationId")
    private Location fromLocation;
    
    @ManyToOne
    @JoinColumn(name = "toLocationId")
    private Location toLocation;
    
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;
    
    private Date movementDate;
    private String movedBy;
    private String notes;
    
    // Getters and Setters
    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public String getMovedBy() {
        return movedBy;
    }

    public void setMovedBy(String movedBy) {
        this.movedBy = movedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}