package com.example.AMS.repository;

import com.example.AMS.model.Condemn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S_CondemnRepository extends JpaRepository<Condemn, String> {
    Condemn findTopByAsset_AssetIdOrderByCondemnDateDesc(String assetId);
}
