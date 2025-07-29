// M_LocationController.java
package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.service.M_LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/adminLocation")
public class M_LocationController {

    private final M_LocationService locationService;

    @Autowired
    public M_LocationController(M_LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public String showLocationManagementPage(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("location", new Location());
        return "Movement/admin/Movement";
    }

    @PostMapping
    public String saveLocation(@ModelAttribute Location location, Model model) {
        locationService.saveLocation(location);
        model.addAttribute("success", true);
        return "redirect:/admin/adminLocation";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("location", locationService.getLocationById(id).orElseThrow());
        model.addAttribute("locations", locationService.getAllLocations());
        return "Movement/admin/Movement";
    }

    @PostMapping("/update")
    public String updateLocation(@ModelAttribute Location location) {
        locationService.saveLocation(location);
        return "redirect:/admin/adminLocation";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable String id) {
        locationService.deleteLocation(id);
        return "redirect:/admin/adminLocation";
    }
}