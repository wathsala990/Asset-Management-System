package com.example.AMS.repository;

import com.example.AMS.model.Asset;
import com.example.AMS.model.M_AssetMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface M_AssetMovementRepository extends JpaRepository<M_AssetMovement, Long> {
       List<M_AssetMovement> findByAssetOrderByMovementDateDesc(Asset asset);
}