package com.example.AMS.service;

import com.example.AMS.dto.AddUserHistoryDto;
import com.example.AMS.dto.L_UserHistoryDto;
import com.example.AMS.dto.UserSuggestDto;
import com.example.AMS.model.Asset;
import com.example.AMS.model.AssetUser;
import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.repository.H_AssetRepository;
import com.example.AMS.repository.L_AssetUserRepository;
import com.example.AMS.repository.M_LocationRepository;
import com.example.AMS.repository.M_RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class L_AssetUserService {
    private final L_AssetUserRepository assetUserRepository;
    private final H_AssetRepository assetRepository;
    private final M_LocationRepository locationRepository;
    private final M_RoomRepository roomRepository;

    public L_AssetUserService(
            L_AssetUserRepository assetUserRepository,
            H_AssetRepository assetRepository,
            M_LocationRepository locationRepository,
            M_RoomRepository roomRepository
    ) {
        this.assetUserRepository = assetUserRepository;
        this.assetRepository = assetRepository;
        this.locationRepository = locationRepository;
        this.roomRepository = roomRepository;
    }

    // Asset auto-suggest
    public List<Asset> suggestAssets(String query) {
        return assetRepository.findByAssetIdContainingIgnoreCaseOrNameContainingIgnoreCase(query, query);
    }

    // User auto-suggest
    public List<UserSuggestDto> suggestUsers(String query) {
        List<AssetUser> users = assetUserRepository.findByUserNameContainingIgnoreCase(query);
        List<UserSuggestDto> dtos = new ArrayList<>();
        for (AssetUser user : users) {
            UserSuggestDto dto = new UserSuggestDto();
            dto.setUserName(user.getUserName());
            dto.setJobRole(user.getJobRole());
            dtos.add(dto);
        }
        return dtos;
    }

    // Add new user history
    @Transactional
    public boolean addAssetUserHistory(AddUserHistoryDto dto) {
        try {
            // Find or create Asset
            Asset asset = assetRepository.findById(dto.getAssetId()).orElse(null);
            if (asset == null) {
                asset = new Asset();
                asset.setAssetId(dto.getAssetId());
                asset.setName(dto.getAssetName());
                asset.setBrand(dto.getAssetBrand());
                asset.setModel(dto.getAssetModel());
                assetRepository.save(asset);
            }

            // Find or create Location by department name
            Location location = null;
            List<Location> locations = locationRepository.findAll();
            for (Location loc : locations) {
                if (loc.getDepartmentName() != null && loc.getDepartmentName().equalsIgnoreCase(dto.getDepartmentName())) {
                    location = loc;
                    break;
                }
            }
            if (location == null) {
                location = new Location();
                location.setDepartmentName(dto.getDepartmentName());
                location = locationRepository.save(location);
            }

            // Find or create Room by room name
            Room room = roomRepository.findByRoomName(dto.getRoomName()).orElse(null);
            if (room == null) {
                room = new Room();
                room.setRoomName(dto.getRoomName());
                room.setLocation(location);
                room = roomRepository.save(room);
            }

            // Save AssetUser with references
            AssetUser assetUser = new AssetUser();
            assetUser.setAsset(asset);
            assetUser.setUserName(dto.getUserName());
            assetUser.setJobRole(dto.getJobRole());
            assetUser.setUserDescription(dto.getUserDescription());
            assetUser.setLocation(location);
            assetUser.setStartDate(dto.getStartDate());
            assetUser.setEndDate(dto.getEndDate());
            assetUserRepository.save(assetUser);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<L_UserHistoryDto> getAllUserHistoryDtos() {
        return assetUserRepository.findAllUserHistoryDtos();
    }

    public List<AssetUser> getUserHistoryByUserName(String userName) {
        return assetUserRepository.findByUserName(userName);
    }

    public List<AssetUser> getAssetHistory(String assetId) {
        return assetUserRepository.findByAsset_AssetId(assetId);
    }

    public AssetUser saveAssetUser(AssetUser assetUser) {
        return assetUserRepository.save(assetUser);
    }

    public AssetUser getUserHistoryById(Long id) {
        return assetUserRepository.findById(id).orElse(null);
    }
}