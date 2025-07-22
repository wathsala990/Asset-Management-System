package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface H_AssetRepository extends JpaRepository<Asset, String> {
    // For asset auto-suggest (case-insensitive contains)
    List<Asset> findByAssetIdContainingIgnoreCaseOrNameContainingIgnoreCase(String assetId, String name);
}
