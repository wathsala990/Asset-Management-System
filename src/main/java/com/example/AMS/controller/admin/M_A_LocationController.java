package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.service.M_LocationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class M_A_LocationController {

    private final M_LocationService locationService;

    public M_A_LocationController(M_LocationService locationService) {
        this.locationService = locationService;
    }

    // Show all locations and provide empty location for modal form
    @GetMapping("/adminMovement")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String showLocations(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("location", new Location());
        return "Movement/admin/Movement";
    }

    // Handle location add from modal form
    @PostMapping("/adminLocation")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String addLocation(@ModelAttribute("location") Location location, Model model) {
        // Check if location ID already exists
        if (locationService.locationExists(location.getLocationId())) {
            model.addAttribute("error", "Location ID already exists");
            model.addAttribute("locations", locationService.getAllLocations());
            return "Movement/admin/Movement";
        }

        locationService.saveLocation(location);
        model.addAttribute("success", true);
        // After adding, reload all locations and show success
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("location", new Location());
        return "Movement/admin/Movement";
    }

    // Show form to edit location
    @GetMapping("/editLocation/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String showEditLocationForm(@PathVariable Long locationId, Model model) {
        Location location = locationService.getLocationById(locationId);
        model.addAttribute("location", location);
        model.addAttribute("locations", locationService.getAllLocations());
        return "Movement/admin/Movement";
    }

    // Process updating location
    @PostMapping("/updateLocation")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String updateLocation(@ModelAttribute Location location, Model model) {
        locationService.saveLocation(location);
        model.addAttribute("success", true);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("location", new Location());
        return "Movement/admin/Movement";
    }

    // Delete location
    @GetMapping("/deleteLocation/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String deleteLocation(@PathVariable Long locationId, Model model) {
        locationService.deleteLocation(locationId);
        model.addAttribute("success", true);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("location", new Location());
        return "Movement/admin/Movement";
    }
}