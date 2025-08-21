package com.example.AMS.repository;

import com.example.AMS.model.AssetUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface L_StaffRepository extends JpaRepository<AssetUser, Long> {
    boolean existsByUserId(String userId);
}
