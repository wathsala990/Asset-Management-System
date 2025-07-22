package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import com.example.AMS.repository.H_VenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_AssetService {

    private final H_AssetRepository assetRepository;

    @Autowired
    public H_AssetService(H_AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    // Get all assets
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // Get asset by ID
    public Optional<Asset> getAssetById(String assetId) {
        return assetRepository.findById(assetId);
    }

    // Create a new asset
    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    // Update an existing asset
    public Asset updateAsset(String assetId, Asset assetDetails) {
        return assetRepository.findById(assetId)
                .map(existingAsset -> {
                    // Update basic information
                    existingAsset.setName(assetDetails.getName());
                    existingAsset.setType(assetDetails.getType());
                    existingAsset.setBrand(assetDetails.getBrand());
                    existingAsset.setModel(assetDetails.getModel());
                    existingAsset.setSpecification(assetDetails.getSpecification());

                    // Update purchase and warranty info
                    existingAsset.setPurchaseDate(assetDetails.getPurchaseDate());
                    existingAsset.setPurchaseStore(assetDetails.getPurchaseStore());
                    existingAsset.setWarrantyId(assetDetails.getWarrantyId());
                    existingAsset.setWarrantyPeriod(assetDetails.getWarrantyPeriod());
                    existingAsset.setWarrantyDate(assetDetails.getWarrantyDate());

                    // Update vendor information
                    existingAsset.setVenderId(assetDetails.getVenderId());
                    existingAsset.setVenderName(assetDetails.getVenderName());
                    existingAsset.setAddress(assetDetails.getAddress());
                    existingAsset.setContactNo(assetDetails.getContactNo());

                    // Update status and other fields
                    existingAsset.setStatus(assetDetails.getStatus());

                    return assetRepository.save(existingAsset);
                })
                .orElseThrow(() -> new RuntimeException("Asset not found with id: " + assetId));
    }

    // Delete an asset
    public void deleteAsset(String assetId) {
        assetRepository.deleteById(assetId);
    }

    // Search assets by ID or name
    public List<Asset> searchAssets(String searchTerm) {
        return assetRepository.findByAssetIdContainingIgnoreCaseOrNameContainingIgnoreCase(searchTerm, searchTerm);
    }

    // Check if asset exists by ID
    public boolean assetExists(String assetId) {
        return assetRepository.existsById(assetId);
    }

    // Get assets by status
    public List<Asset> getAssetsByStatus(String activityStatus) {
        return assetRepository.findByStatus(activityStatus);
    }

    // Get assets by vendor ID
    public List<Asset> getAssetsByVenderId(String venderId) {
        return assetRepository.findByVenderId(venderId);
    }
}