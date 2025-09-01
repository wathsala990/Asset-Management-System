package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.model.M_AssetMovement;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Optional;
import com.example.AMS.repository.M_AssetMovementRepository;
import com.example.AMS.repository.M_LocationRepository;

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

    // Helper method to add all required model attributes
    private void populateModelAttributes(Model model) {
        try {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("rooms", roomService.getAllRooms());
            model.addAttribute("movements", movementService.getAllMovements());
            model.addAttribute("assets", assetService.getAllAssets());
            model.addAttribute("location", new Location());
            model.addAttribute("room", new Room());
        } catch (Exception e) {
            // Log error but don't throw exception to avoid template parsing issues
            System.err.println("Error populating model attributes: " + e.getMessage());
            // Add empty collections to avoid null pointers in template
            model.addAttribute("locations", java.util.Collections.emptyList());
            model.addAttribute("rooms", java.util.Collections.emptyList());
            model.addAttribute("movements", java.util.Collections.emptyList());
            model.addAttribute("assets", java.util.Collections.emptyList());
        }
    }

    @GetMapping
    public String showMovementPage(Model model) {
        populateModelAttributes(model);
        return "Movement/admin/Movement";
    }

    @PostMapping("/addLocation")
    public String addLocation(@Valid @ModelAttribute("location") Location location, 
                             BindingResult result, 
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateModelAttributes(model);
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "Movement/admin/Movement";
        }
        
        try {
            locationService.saveLocation(location);
            redirectAttributes.addFlashAttribute("successMessage", "Location added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding location: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @PostMapping("/addRoom")
    public String addRoom(@Valid @ModelAttribute("room") Room room, 
                         BindingResult result, 
                         Model model,
                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            populateModelAttributes(model);
            model.addAttribute("errorMessage", "Please correct the errors in the form.");
            return "Movement/admin/Movement";
        }
        
        try {
            roomService.saveRoom(room);
            redirectAttributes.addFlashAttribute("successMessage", "Room added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding room: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @PostMapping("/allocateAsset")
    public String allocateAsset(@RequestParam String assetId,
                               @RequestParam String locationId,
                               @RequestParam(required = false) String roomId,
                               @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date allocationDate,
                               @RequestParam(required = false) String notes,
                               RedirectAttributes redirectAttributes) {
        try {
            movementService.allocateAsset(assetId, locationId, roomId, allocationDate, notes);
            redirectAttributes.addFlashAttribute("successMessage", "Asset allocated successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while allocating the asset: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @PostMapping("/location/update")
    public String updateLocation(@RequestParam String locationId,
                                @RequestParam String departmentName,
                                @RequestParam(required = false) String description,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date transferDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                RedirectAttributes redirectAttributes) {
        try {
            Location location = locationService.getLocationById(locationId);
            if (location != null) {
                location.setDepartmentName(departmentName);
                location.setDescription(description);
                location.setTransferDate(transferDate);
                location.setStartDate(startDate);
                location.setEndDate(endDate);
                locationService.saveLocation(location);
                redirectAttributes.addFlashAttribute("successMessage", "Location updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Location not found with ID: " + locationId);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating location: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @PostMapping("/room/update")
    public String updateRoom(@RequestParam String roomId,
                            @RequestParam String roomName,
                            @RequestParam String locationId,
                            RedirectAttributes redirectAttributes) {
        try {
            Optional<Room> roomOpt = roomService.getRoomById(roomId);
            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                room.setRoomName(roomName);
                
                Location location = locationService.getLocationById(locationId);
                if (location != null) {
                    room.setLocation(location);
                    roomService.saveRoom(room);
                    redirectAttributes.addFlashAttribute("successMessage", "Room updated successfully!");
                } else {
                    redirectAttributes.addFlashAttribute("errorMessage", "Location not found with ID: " + locationId);
                }
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Room not found with ID: " + roomId);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating room: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @PostMapping("/movement/update")
    public String updateMovement(@RequestParam Long movementId,
                                @RequestParam String assetId,
                                @RequestParam String fromLocationId,
                                @RequestParam String toLocationId,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date movementDate,
                                @RequestParam(required = false) String notes,
                                RedirectAttributes redirectAttributes) {
        try {
            Optional<M_AssetMovement> movementOpt = movementRepository.findById(movementId);
            if (movementOpt.isPresent()) {
                M_AssetMovement movement = movementOpt.get();
                
                // Update asset if provided
                if (assetId != null && !assetId.isBlank()) {
                    assetService.getAssetById(assetId).ifPresent(movement::setAsset);
                }
                
                // Update from location if provided
                if (fromLocationId != null && !fromLocationId.isBlank()) {
                    locationRepository.findById(fromLocationId).ifPresent(movement::setFromLocation);
                }
                
                // Update to location if provided
                if (toLocationId != null && !toLocationId.isBlank()) {
                    locationRepository.findById(toLocationId).ifPresent(movement::setToLocation);
                }
                
                movement.setMovementDate(movementDate);
                movement.setNotes(notes);
                
                movementRepository.save(movement);
                redirectAttributes.addFlashAttribute("successMessage", "Movement record updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Movement record not found with ID: " + movementId);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating movement record: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @GetMapping("/location/delete/{locationId}")
    public String deleteLocation(@PathVariable String locationId, RedirectAttributes redirectAttributes) {
        try {
            locationService.softDeleteLocation(locationId);
            redirectAttributes.addFlashAttribute("successMessage", "Location deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting location: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @GetMapping("/room/delete/{roomId}")
    public String deleteRoom(@PathVariable String roomId, RedirectAttributes redirectAttributes) {
        try {
            roomService.deleteRoom(roomId);
            redirectAttributes.addFlashAttribute("successMessage", "Room deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting room: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }

    @GetMapping("/movement/delete/{movementId}")
    public String deleteMovement(@PathVariable Long movementId, RedirectAttributes redirectAttributes) {
        try {
            movementRepository.deleteById(movementId);
            redirectAttributes.addFlashAttribute("successMessage", "Movement record deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting movement record: " + e.getMessage());
        }
        
        return "redirect:/adminMovement";
    }
}