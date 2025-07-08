package com.example.AMS.repository;

import com.example.AMS.model.YearlyVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface H_YearlyVerificationRepository extends JpaRepository<YearlyVerification, String> {
    List<YearlyVerification> findByAsset_AssetID(String assetId);
    List<YearlyVerification> findByUser_UserID(String userId);

    @Query("SELECT y FROM YearlyVerification y WHERE y.verificationDate BETWEEN :startDate AND :endDate")
    List<YearlyVerification> findVerificationsByDateRange(Date startDate, Date endDate);

    List<YearlyVerification> findByVerification(Boolean verificationStatus);

    Long countByVerification(boolean b);
}