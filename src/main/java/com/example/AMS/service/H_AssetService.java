package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_AssetService {

    @Autowired
    private H_AssetRepository assetRepository;

    // Get all assets
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    // Get asset by ID
    public Asset getAssetById(String assetId) {
        Optional<Asset> optionalAsset = assetRepository.findById(assetId);
        return optionalAsset.orElse(null);
    }

    // Save or update an asset
    public void saveAsset(Asset asset) {
        assetRepository.save(asset);
    }

    // Delete asset by ID
    public void deleteAsset(String assetId) {
        assetRepository.deleteById(assetId);
    }
}
