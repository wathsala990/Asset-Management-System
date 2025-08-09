package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.service.M_LocationService;
import com.example.AMS.service.M_RoomService;
import com.example.AMS.service.M_AssetMovementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/adminMovement")
public class M_AssetMovementController {

    @Autowired
    private M_LocationService locationService;
    @Autowired
    private M_RoomService roomService;
    @Autowired
    private M_AssetMovementService movementService;

    @GetMapping
    public String showMovementPage(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("movements", movementService.getAllMovements());
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
                            @RequestParam String fromLocationId,
                            @RequestParam String toLocationId,
                            @RequestParam String roomId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date allocationDate,
                            @RequestParam(required = false) String notes) {
        movementService.allocateAsset(assetId, fromLocationId, toLocationId, allocationDate, notes);
        return "redirect:/adminMovement";
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
}