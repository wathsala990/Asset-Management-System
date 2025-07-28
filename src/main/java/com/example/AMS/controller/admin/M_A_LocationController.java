package com.example.AMS.controller.admin;

import com.example.AMS.model.Location;
import com.example.AMS.service.M_LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class M_A_LocationController {

    @Autowired
    private M_LocationService locationService;

    @GetMapping
    public String showMovementPage(Model model) {
        List<Location> locations = locationService.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("newLocation", new Location());
        return "Movement/admin/Movement";
    }

    @PostMapping("/location/save")
    public String saveLocation(@ModelAttribute("newLocation") Location location) {
        locationService.saveLocation(location);
        return "redirect:/adminMovement";
    }

    @GetMapping("/location/edit/{id}")
    @ResponseBody
    public Location getLocationById(@PathVariable("id") Long id) {
        return locationService.getLocationById(id);
    }

    @PostMapping("/location/update")
    public String updateLocation(@ModelAttribute Location location) {
        locationService.saveLocation(location);
        return "redirect:/adminMovement";
    }

    @GetMapping("/location/delete/{id}")
    public String deleteLocation(@PathVariable("id") Long id) {
        locationService.deleteLocation(id);
        return "redirect:/adminMovement";
    }
}
