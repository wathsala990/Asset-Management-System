package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface H_AssetRepository extends JpaRepository<Asset, String> {

    List<Asset> findByActiveStatusTrue();

    List<Asset> findByLocation_LocationID(String locationID);

    List<Asset> findByAssetNameContainingIgnoreCase(String keyword);

    List<Asset> findByAssetType(String assetType);

    List<Asset> findByWarrantyDateBefore(Date date);

    List<Asset> findByPurchaseDateBetween(Date start, Date end);
}
