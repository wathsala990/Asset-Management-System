package com.example.AMS.service;

import com.example.AMS.model.YearlyVerification;
import com.example.AMS.repository.H_YearlyVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class H_YearlyVerificationService {
    @Autowired
    private H_YearlyVerificationRepository  verificationRepository;

    @Autowired
    private H_AssetService  assetService;

    @Autowired
    private H_UserService userService;

    public List<YearlyVerification> getAllVerifications() {
        return verificationRepository.findAll();
    }

    public YearlyVerification getVerificationById(String verificationId) {
        return verificationRepository.findById(verificationId).orElse(null);
    }

    public YearlyVerification saveVerification(YearlyVerification verification) {
        return verificationRepository.save(verification);
    }

    public void deleteVerification(String verificationId) {
        verificationRepository.deleteById(verificationId);
    }

    public List<YearlyVerification> getVerificationsByAsset(String assetId) {
        return verificationRepository.findByAsset_AssetID(assetId);
    }

    public List<YearlyVerification> getVerificationsByUser(String userId) {
        return verificationRepository.findByUser_UserID(userId);
    }

    public Long getVerifiedAssetsCount() {
        return verificationRepository.countByVerification(true);
    }
}