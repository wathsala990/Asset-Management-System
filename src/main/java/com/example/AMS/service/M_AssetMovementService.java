// M_AssetMovementService.java
package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.model.M_AssetMovement;
import com.example.AMS.repository.M_AssetMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class M_AssetMovementService {

    private final M_AssetMovementRepository movementRepository;

    @Autowired
    public M_AssetMovementService(M_AssetMovementRepository movementRepository) {
        this.movementRepository = movementRepository;
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
}