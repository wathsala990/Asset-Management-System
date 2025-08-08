package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface M_AssetRepository extends JpaRepository<Asset, String> {
    // Add custom query methods if needed
}