package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class H_AssetService {

    private final H_AssetRepository assetRepository;

    public H_AssetService(H_AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(String assetID) {
        return assetRepository.findById(assetID);
    }

    public void saveAsset(Asset asset) {
        assetRepository.save(asset);
    }

    public void updateAsset(String assetID, Asset updatedAsset) {
        assetRepository.save(updatedAsset); // save() updates if ID exists
    }

    public void deleteAsset(String assetID) {
        assetRepository.deleteById(assetID);
    }
}
