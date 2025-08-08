package com.example.AMS.controller.admin;

import com.example.AMS.model.*;
import com.example.AMS.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/adminMovement")
public class M_AssetMovementController {

    @Autowired
    private M_AssetService assetService;

    @Autowired
    private M_LocationService locationService;

    @Autowired
    private M_RoomService roomService;

    @Autowired
    private M_AssetMovementService movementService;

    @GetMapping
    public String showMovementPage(Model model) {
        List<Location> locations = locationService.getAllLocations();
        List<Room> rooms = roomService.getAllRooms();
        List<M_AssetMovement> movements = movementService.getAllMovements();

        model.addAttribute("locations", locations);
        model.addAttribute("rooms", rooms);
        model.addAttribute("movements", movements);

        return "Movement/admin/Movement";
    }

    @PostMapping("/allocateAsset")
    public String allocateAsset(@RequestParam String assetId,
                               @RequestParam String locationId, // Change to String
                               @RequestParam(required = false) String roomId,
                               @RequestParam("allocationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date allocationDate,
                               @RequestParam(required = false) String notes,
                               Principal principal) {
        Asset asset = assetService.getAssetById(assetId).orElseThrow();
        Location fromLocation = asset.getLocation();
        Location toLocation = locationService.getLocationById(locationId).orElseThrow(); // Pass String
        Room room = (roomId != null && !roomId.isEmpty()) ? roomService.getRoomById(roomId).orElse(null) : null;

        asset.setLocation(toLocation);
        asset.setRoom(room);
        assetService.saveAsset(asset);

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

    @PostMapping("/addLocation")
    public String addLocation(@ModelAttribute Location location) {
        locationService.saveLocation(location);
        return "redirect:/adminMovement";
    }

    @PostMapping("/addRoom")
    public String addRoom(@ModelAttribute Room room, @RequestParam String locationId) { // Change to String
        Location location = locationService.getLocationById(locationId).orElseThrow(); // Pass String
        room.setLocation(location);
        roomService.saveRoom(room);
        return "redirect:/adminMovement";
    }

    // Optionally, add edit/update/delete methods for locations and rooms here if you want to handle them on the same page.
    
}