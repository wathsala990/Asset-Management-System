package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class H_AssetService {
    @Autowired
    private H_AssetRepository assetRepository;

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Asset getAssetById(String assetId) {
        return assetRepository.findById(assetId).orElse(null);
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public void deleteAsset(String assetId) {
        assetRepository.deleteById(assetId);
    }

    public List<Asset> getActiveAssets() {
        return assetRepository.findByActiveStatus(true);
    }

    public List<Asset> getAssetsByType(String assetType) {
        return assetRepository.findByAssetType(assetType);
    }

    public List<Asset> searchAssetsByName(String assetName) {
        return assetRepository.findByAssetNameContaining(assetName);
    }
}