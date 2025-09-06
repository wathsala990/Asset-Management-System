// com.example.AMS.repository.L_AssetUserRepository
package com.example.AMS.repository;

import com.example.AMS.dto.L_UserHistoryDto;
import com.example.AMS.model.AssetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface L_AssetUserRepository extends JpaRepository<AssetUser, Long> {
    // For user name auto-suggest (case-insensitive contains)
    List<AssetUser> findByUserNameContainingIgnoreCase(String userName);
    List<AssetUser> findByUserName(String userName);
    List<AssetUser> findByAsset_AssetId(String assetId);
    List<AssetUser> findAllByOrderByUserNameAscStartDateDesc();


    // DTO-based query to fetch all user history records with asset, location, and room details
    @Query("SELECT au.userName as userName, au.jobRole as jobRole, au.userDescription as userDescription, " +
           "a.assetId as assetId, a.name as assetName, a.serialNumber as assetSerialNumber, a.model as assetModel, " +
           "l.departmentName as departmentName, r.roomName as roomName, " +
           "au.startDate as startDate, au.endDate as endDate " +
           "FROM AssetUser au " +
           "LEFT JOIN au.asset a " +
           "LEFT JOIN au.location l " +
           "LEFT JOIN Room r ON r.location = l")
    List<L_UserHistoryDto> findAllUserHistoryDtos();



}

