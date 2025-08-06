// M_RoomController.java
package com.example.AMS.controller.admin;

import com.example.AMS.model.Room;
import com.example.AMS.service.M_LocationService;
import com.example.AMS.service.M_RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminRoom")
public class M_RoomController {

    private final M_RoomService roomService;
    private final M_LocationService locationService;

    @Autowired
    public M_RoomController(M_RoomService roomService, M_LocationService locationService) {
        this.roomService = roomService;
        this.locationService = locationService;
    }

    @GetMapping
    public String showRoomManagementPage(Model model) {
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("room", new Room());
        return "Movement/admin/Movement";
    }

    @PostMapping
    public String saveRoom(@ModelAttribute Room room, Model model) {
        roomService.saveRoom(room);
        model.addAttribute("success", true);
        return "redirect:/admin/adminRoom";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("room", roomService.getRoomById(id).orElseThrow());
        model.addAttribute("rooms", roomService.getAllRooms());
        model.addAttribute("locations", locationService.getAllLocations());
        return "Movement/admin/Movement";
    }

    @PostMapping("/update")
    public String updateRoom(@ModelAttribute Room room) {
        roomService.saveRoom(room);
        return "redirect:/admin/adminRoom";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return "redirect:/admin/adminRoom";
    }

    @PostMapping("/adminRoom")
    public String addRoom(@ModelAttribute Room room, @RequestParam String location) {
        room.setLocation(locationService.getLocationById(location).orElse(null));
        roomService.saveRoom(room);
        return "redirect:/adminMovement";
    }
}