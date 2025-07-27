package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.model.Room;
import com.example.AMS.service.M_LocationService;
import com.example.AMS.service.M_RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class M_A_LocationController {

    private final M_LocationService locationService;
    private final M_RoomService roomService;

    @Autowired
    public M_A_LocationController(M_LocationService locationService, M_RoomService roomService) {
        this.locationService = locationService;
        this.roomService = roomService;
    }

    @GetMapping("/adminMovement")
    public String showMovementPage(Model model) {
        model.addAttribute("location", new Location());
        model.addAttribute("room", new Room());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("rooms", roomService.getAllRooms());
        return "Movement/admin/Movement";
    }

    @PostMapping("/adminLocation")
    public String addLocation(@ModelAttribute("location") Location location, Model model) {
        locationService.saveLocation(location);
        model.addAttribute("success", true);
        return "redirect:/Movement/admin/Movement";
    }

    @PostMapping("/adminRoom")
    public String addRoom(@ModelAttribute("room") Room room, Model model) {
        roomService.saveRoom(room);
        model.addAttribute("success", true);
        return "redirect:/Movement/admin/Movement";
    }
}
