package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class H_AssetService {
    private final H_AssetRepository assetRepository;

    public H_AssetService(H_AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public Asset saveAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
}
