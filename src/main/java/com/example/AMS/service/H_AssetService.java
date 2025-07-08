package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.model.Location;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_AssetService {

    private final H_AssetRepository assetRepository;
    private final M_LocationService locationService;  // Add this dependency

    public H_AssetService(H_AssetRepository assetRepository,
                          M_LocationService locationService) {  // Update constructor
        this.assetRepository = assetRepository;
        this.locationService = locationService;  // Initialize locationService
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(String assetID) {
        return assetRepository.findById(assetID);
    }

    public Asset saveAsset(Asset asset) {
        if (asset.getLocation() != null && asset.getLocation().getLocationID() != null) {
            // Use the injected locationService instance instead of static call
            Location location = locationService.getLocationById(asset.getLocation().getLocationID())
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            asset.setLocation(location);
        }
        return assetRepository.save(asset);
    }

    public Asset updateAsset(String assetID, Asset updatedAsset) {
        updatedAsset.setAssetID(assetID);  // Ensure the ID is set
        return assetRepository.save(updatedAsset);  // Return the updated asset
    }

    public void deleteAsset(String assetID) {
        assetRepository.deleteById(assetID);
    }
}