package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface H_AssetRepository extends JpaRepository<Asset, String> {
    List<Asset> findByActiveStatus(Boolean activeStatus);
    List<Asset> findByAssetType(String assetType);
    List<Asset> findByAssetNameContaining(String assetName);

    @Query("SELECT a FROM Asset a WHERE a.purchaseDate BETWEEN :startDate AND :endDate")
    List<Asset> findByPurchaseDateBetween(Date startDate, Date endDate);

    List<Asset> findByLocation_LocationID(String locationId);
}