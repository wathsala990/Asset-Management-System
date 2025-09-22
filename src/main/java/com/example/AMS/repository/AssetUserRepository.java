package com.example.AMS.repository;

import com.example.AMS.model.AssetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssetUserRepository extends JpaRepository<AssetUser, Long> {
    List<AssetUser> findByUserNameContainingIgnoreCase(String userName);
}
