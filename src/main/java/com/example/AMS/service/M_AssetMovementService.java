// M_AssetMovementService.java
package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.model.M_AssetMovement;
import com.example.AMS.repository.M_AssetMovementRepository;
import com.example.AMS.repository.M_AssetRepository;
import com.example.AMS.repository.M_LocationRepository;
import com.example.AMS.repository.M_RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class M_AssetMovementService {

    private final M_AssetMovementRepository movementRepository;
    private final M_AssetRepository assetRepository;
    private final M_LocationRepository locationRepository;
    private final M_RoomRepository roomRepository;

    @Autowired
    public M_AssetMovementService(M_AssetMovementRepository movementRepository, M_AssetRepository assetRepository, M_LocationRepository locationRepository, M_RoomRepository roomRepository) {
        this.movementRepository = movementRepository;
        this.assetRepository = assetRepository;
        this.locationRepository = locationRepository;
        this.roomRepository = roomRepository;
    }

    public List<M_AssetMovement> getAllMovements() {
        return movementRepository.findAll();
    }

    public M_AssetMovement saveMovement(M_AssetMovement movement) {
        return movementRepository.save(movement);
    }

    public List<M_AssetMovement> getMovementsByAsset(Asset asset) {
        return movementRepository.findByAssetOrderByMovementDateDesc(asset);
    }

    // Corrected allocateAsset method
    public void allocateAsset(String assetId, String locationId, String roomId, Date allocationDate, String notes) {
        // Fetch asset, location, room
        Asset asset = assetRepository.findById(assetId).orElse(null);
        if (asset == null) {
            throw new IllegalArgumentException("Asset not found with ID: " + assetId);
        }
        
        Location toLocation = locationRepository.findById(locationId).orElse(null);
        if (toLocation == null) {
            throw new IllegalArgumentException("Location not found with ID: " + locationId);
        }
        
        Room room = null;
        if (roomId != null && !roomId.isBlank()) {
            room = roomRepository.findById(roomId).orElse(null);
            if (room == null) {
                throw new IllegalArgumentException("Room not found with ID: " + roomId);
            }
        }
        
        // Create movement record
        M_AssetMovement movement = new M_AssetMovement();
        
        // The fromLocation should be the asset's current location
        Location fromLocation = asset.getLocation();
        
        movement.setAsset(asset);
        movement.setFromLocation(fromLocation);
        movement.setToLocation(toLocation);
        if (room != null) {
            movement.setRoom(room);
        }
        // If allocationDate is null, use current date
        movement.setMovementDate(allocationDate != null ? allocationDate : new Date());
        movement.setNotes(notes);

        // Save the movement first
        movementRepository.save(movement);
        
        // Update asset current state to reflect the allocation
        asset.setLocation(toLocation);
        asset.setRoom(room);
        assetRepository.save(asset);
    }
}