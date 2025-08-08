package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.M_AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class M_AssetService {

    private final M_AssetRepository assetRepository;

    @Autowired
    public M_AssetService(M_AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(String assetId) {
        return assetRepository.findById(assetId);
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public void deleteAsset(String assetId) {
        assetRepository.deleteById(assetId);
    }
}