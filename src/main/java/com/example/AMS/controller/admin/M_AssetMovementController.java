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

import java.security.Principal;
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
    public String addRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/adminMovement";
    }

    @PostMapping("/allocateAsset")
    public String allocateAsset(@RequestParam String assetId,
                               @RequestParam String locationId,
                               @RequestParam(required = false) String roomId,
                               @RequestParam("allocationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date allocationDate,
                               @RequestParam(required = false) String notes,
                               Principal principal) {
        movementService.allocateAsset(assetId, locationId, roomId, allocationDate, notes, principal.getName());
        return "redirect:/adminMovement";
    }
}