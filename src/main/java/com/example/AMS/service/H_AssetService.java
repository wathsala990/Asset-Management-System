package com.example.AMS.service;

import com.example.AMS.model.Asset;
import com.example.AMS.repository.H_AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class H_AssetService {

    @Autowired
    private H_AssetRepository assetRepository;

    public Asset createAsset(Asset asset) {
        return assetRepository.save(asset);
    }

    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }

    public Optional<Asset> getAssetById(String assetID) {
        return assetRepository.findById(assetID);
    }

    public Asset updateAsset(String assetID, Asset updatedAsset) {
        if (assetRepository.existsById(assetID)) {
            updatedAsset.setAssetID(assetID);
            return assetRepository.save(updatedAsset);
        }
        return null;
    }

    public void deleteAsset(String assetID) {
        assetRepository.deleteById(assetID);
    }

    public void importFromExcel(MultipartFile file) {
    }

    public void saveAsset(Asset asset) {
        assetRepository.save(asset); // assuming you have a JPA repository named assetRepository
    }

}
