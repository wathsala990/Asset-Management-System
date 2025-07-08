package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.M_LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List; // Add this import

@Controller
@RequestMapping("/Asset")
public class H_AssetController {
    @Autowired
    private H_AssetService assetService;

    @Autowired
    private M_LocationService locationService;

    @GetMapping
    public String listAssets(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        return "Asset/Asset_home";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("assetTypes", List.of("Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"));
        return "Asset/Asset_create";
    }

    @PostMapping("/save")
    public String saveAsset(@ModelAttribute Asset asset) {
        assetService.saveAsset(asset);
        return "redirect:/Asset";
    }

    @GetMapping("/edit/{assetID}")
    public String showEditForm(@PathVariable String assetID, Model model) {
        Asset asset = assetService.getAssetById(assetID);
        model.addAttribute("asset", asset);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("assetTypes", List.of("Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"));
        return "Asset/Asset_edit";
    }

    @GetMapping("/delete/{assetID}")
    public String deleteAsset(@PathVariable String assetID) {
        assetService.deleteAsset(assetID);
        return "redirect:/Asset";
    }

    @GetMapping("/search")
    public String searchAssets(@RequestParam String name, Model model) {
        model.addAttribute("assets", assetService.searchAssetsByName(name));
        return "Asset/Asset_home";
    }

    @GetMapping("/active")
    public String listActiveAssets(Model model) {
        model.addAttribute("assets", assetService.getActiveAssets());
        return "Assets/Asset_home";
    }
}