package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.service.M_LocationService;
import com.example.AMS.service.M_RoomService;
import com.example.AMS.service.M_AssetMovementService;
import com.example.AMS.service.M_AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import com.example.AMS.repository.M_AssetMovementRepository;
import com.example.AMS.repository.M_LocationRepository;
import com.example.AMS.model.M_AssetMovement;

@Controller
@RequestMapping("/adminMovement")
public class M_AssetMovementController {

    @Autowired
    private M_LocationService locationService;
    @Autowired
    private M_RoomService roomService;
    @Autowired
    private M_AssetMovementService movementService;
    @Autowired
    private M_AssetService assetService;
    @Autowired
    private M_AssetMovementRepository movementRepository;
    @Autowired
    private M_LocationRepository locationRepository;

    @GetMapping
    public String showMovementPage(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("movements", movementService.getAllMovements());
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("location", new Location());
        model.addAttribute("room", new Room());
        return "Movement/admin/Movement";
    }

    @PostMapping("/addLocation")
    public String addLocation(@Valid @ModelAttribute("location") Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("movements", movementService.getAllMovements());
            model.addAttribute("room", new Room());
            return "Movement/admin/Movement";
        }
        locationService.saveLocation(location);
        return "redirect:/adminMovement";
    }

    @PostMapping("/addRoom")
    public String addRoom(@Valid @ModelAttribute("room") Room room, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("movements", movementService.getAllMovements());
            model.addAttribute("location", new Location());
            return "Movement/admin/Movement";
        }
        roomService.saveRoom(room);
        return "redirect:/adminMovement";
    }

    @PostMapping("/allocateAsset")
    public String allocateAsset(@RequestParam String assetId,
                            @RequestParam String locationId,
                            @RequestParam String roomId,
                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date allocationDate,
                            @RequestParam(required = false) String notes) {
        movementService.allocateAsset(assetId, locationId, roomId, allocationDate, notes);
        return "redirect:/adminMovement?tab=movement";
    }

    @GetMapping("/location/view/{locationId}")
    public String viewLocation(@PathVariable String locationId, Model model) {
        Location location = locationService.getLocationById(locationId);
        model.addAttribute("location", location);
        // Add other needed attributes (rooms, movements, etc.)
        return "Movement/admin/LocationView"; // Create this Thymeleaf template
    }

    @GetMapping("/location/edit/{locationId}")
    public String editLocationForm(@PathVariable String locationId, Model model) {
        Location location = locationService.getLocationById(locationId);
        model.addAttribute("location", location);
        return "Movement/admin/LocationEdit"; // Create this Thymeleaf template
    }

    @PostMapping("/location/edit/{locationId}")
    public String editLocation(@PathVariable String locationId, @Valid @ModelAttribute("location") Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Movement/admin/LocationEdit";
        }
        locationService.updateLocation(locationId, location);
        return "redirect:/adminMovement";
    }

    @GetMapping("/location/delete/{locationId}")
    public String deleteLocation(@PathVariable String locationId) {
        locationService.softDeleteLocation(locationId); // Implement soft delete in your service
        return "redirect:/adminMovement";
    }

    @PostMapping("/location/update")
    public String updateLocation(@ModelAttribute("location") Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("movements", movementService.getAllMovements());
            model.addAttribute("room", new Room());
            return "Movement/admin/Movement";
        }
        locationService.updateLocation(location.getLocationId(), location);
        return "redirect:/adminMovement";
    }

    @PostMapping("/room/update")
    public String updateRoom(@RequestParam String roomId, @RequestParam String roomName) {
        roomService.getRoomById(roomId).ifPresent(room -> {
            room.setRoomName(roomName);
            roomService.saveRoom(room);
        });
        return "redirect:/adminMovement";
    }

    @GetMapping("/room/delete/{roomId}")
    public String deleteRoom(@PathVariable String roomId) {
        roomService.deleteRoom(roomId);
        return "redirect:/adminMovement";
    }

    @PostMapping("/movement/update")
    public String updateMovement(@RequestParam Long movementId,
                                 @RequestParam(required = false) String toLocationId,
                                 @RequestParam(required = false) String fromLocationId,
                                 @RequestParam(required = false) String assetId,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date movementDate,
                                 @RequestParam(required = false) String notes) {
        movementRepository.findById(movementId).ifPresent(m -> {
            if (assetId != null && !assetId.isBlank()) {
                assetService.getAssetById(assetId).ifPresent(m::setAsset);
            }
            if (fromLocationId != null && !fromLocationId.isBlank()) {
                locationRepository.findById(fromLocationId).ifPresent(m::setFromLocation);
            }
            if (toLocationId != null && !toLocationId.isBlank()) {
                locationRepository.findById(toLocationId).ifPresent(m::setToLocation);
            }
            m.setMovementDate(movementDate);
            if (notes != null) {
                m.setNotes(notes);
            }
            movementRepository.save(m);
        });
        return "redirect:/adminMovement?tab=movement";
    }

    @GetMapping("/movement/delete/{movementId}")
    public String deleteMovement(@PathVariable Long movementId) {
        movementRepository.deleteById(movementId);
        return "redirect:/adminMovement?tab=movement";
    }
}