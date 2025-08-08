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

    public void allocateAsset(String assetId, String locationId, String roomId, Date allocationDate, String notes, String movedBy) {
        Asset asset = assetRepository.findById(assetId).orElse(null);
        Location toLocation = locationRepository.findById(locationId).orElse(null);
        Room room = (roomId != null && !roomId.isEmpty()) ? roomRepository.findById(roomId).orElse(null) : null;

        M_AssetMovement movement = new M_AssetMovement();
        movement.setAsset(asset);
        movement.setFromLocation(asset != null ? asset.getLocation() : null);
        movement.setToLocation(toLocation);
        movement.setRoom(room);
        movement.setMovementDate(allocationDate);
        // If you have notes and movedBy fields in M_AssetMovement, set them here:
        // movement.setNotes(notes);
        // movement.setMovedBy(movedBy);

        movementRepository.save(movement);

        // Optionally update asset's location and room
        if (asset != null) {
            asset.setLocation(toLocation);
            asset.setRoom(room);
            assetRepository.save(asset);
        }
    }
}