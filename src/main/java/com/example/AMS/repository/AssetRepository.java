package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, String> {
    List<Asset> findByAssetIdContainingIgnoreCaseOrNameContainingIgnoreCase(String assetId, String name);
}
