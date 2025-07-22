package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface H_AssetRepository extends JpaRepository<Asset, String> {
    // You can add custom query methods here if needed
    List<Asset> findByAssetIdContainingIgnoreCaseOrNameContainingIgnoreCase(String assetId, String name);
}
