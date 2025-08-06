package com.example.AMS.controller.admin;

import com.example.AMS.model.*;
import com.example.AMS.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

@Controller
@RequestMapping("/adminMovement") 
public class M_AssetMovementController {

    private final M_AssetMovementService movementService;
    private final H_AssetService assetService;
    private final M_LocationService locationService;
    private final M_RoomService roomService;

    @Autowired
    public M_AssetMovementController(M_AssetMovementService movementService, 
                                    H_AssetService assetService, 
                                    M_LocationService locationService, 
                                    M_RoomService roomService) {
        this.movementService = movementService;
        this.assetService = assetService;
        this.locationService = locationService;
        this.roomService = roomService;
    }

    @GetMapping
    public String showMovementManagementPage(Model model) {
        model.addAttribute("movements", movementService.getAllMovements());
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "Movement/admin/Movement";  // Verify this template path is correct
    }

    @PostMapping("/allocateAsset")
    public String allocateAsset(@RequestParam String assetId,
                               @RequestParam String locationId,
                               @RequestParam(required = false) String roomId,
                               @RequestParam("allocationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date allocationDate,
                               @RequestParam(required = false) String notes,
                               Principal principal) {
        Asset asset = assetService.getAssetById(assetId).orElseThrow();
        Location fromLocation = asset.getLocation();
        Location toLocation = locationService.getLocationById(locationId).orElseThrow();
        Room room = (roomId != null && !roomId.isEmpty()) ? roomService.getRoomById(roomId).orElse(null) : null;

        // Update asset's current location and room
        asset.setLocation(toLocation);
        asset.setRoom(room);
        assetService.saveAsset(asset);

        // Record the movement
        M_AssetMovement movement = new M_AssetMovement();
        movement.setAsset(asset);
        movement.setFromLocation(fromLocation);
        movement.setToLocation(toLocation);
        movement.setRoom(room);
        movement.setMovementDate(allocationDate);
        movement.setMovedBy(principal.getName());
        movement.setNotes(notes);

        movementService.saveMovement(movement);

        return "redirect:/adminMovement";
    }
}