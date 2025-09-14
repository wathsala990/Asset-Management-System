package com.example.AMS.service;

import com.example.AMS.dto.StaffDto;
import com.example.AMS.model.AssetUser;
import com.example.AMS.repository.L_StaffRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class L_StaffService {
    private final L_StaffRepository staffRepository;

    public L_StaffService(L_StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<StaffDto> getAllStaff() {
        return staffRepository.findAll().stream()
            .map(user -> new StaffDto(
                user.getUserId(),
                user.getUserName(),
                user.getJobRole(),
                user.getUserDescription()
            ))
            .collect(Collectors.toList());
    }

    @Transactional
    public boolean addStaff(StaffDto dto) {
        try {
            // Check if userId already exists
            boolean exists = staffRepository.existsByUserId(dto.getUserId());
            if (exists) {
                return false;
            }
            AssetUser user = new AssetUser();
            user.setUserId(dto.getUserId());
            user.setUserName(dto.getUserName());
            user.setJobRole(dto.getJobRole());
            user.setUserDescription(dto.getUserDescription());
            staffRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean editStaff(StaffDto dto) {
        try {
            // Find existing staff by userId
            AssetUser existingUser = staffRepository.findByUserId(dto.getUserId());
            if (existingUser == null) {
                return false; // User not found
            }
            
            // Update the fields
            existingUser.setUserName(dto.getUserName());
            existingUser.setJobRole(dto.getJobRole());
            existingUser.setUserDescription(dto.getUserDescription());
            
            staffRepository.save(existingUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
