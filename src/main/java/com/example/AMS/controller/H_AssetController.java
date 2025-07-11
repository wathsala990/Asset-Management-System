package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.M_LocationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/Asset")
public class H_AssetController {
    @Autowired
    private H_AssetService assetService;

    @Autowired
    private M_LocationService locationService;

    // Home page - List all assets
    @GetMapping("/Asset")
    public String getAssetsPage(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("newAsset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        return "Asset_home";
    }

    // Show add form
    @GetMapping("/create")
    public String showAddForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("assetTypes", List.of("Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"));
        return "Asset/Asset_create";
    }

    // Save new asset or update existing one
    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "Asset/Asset_create";
        }

        assetService.saveAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset information saved successfully");
        return "redirect:/Asset";
    }

    // Show edit form
    @GetMapping("/Asset_edit/{assetID}")
    public String showEditForm(@PathVariable String assetID, Model model) {
        Asset asset = assetService.getAssetById(assetID);
        model.addAttribute("asset", asset);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("assetTypes", List.of("Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"));
        return "Asset/Asset_edit";
    }

    // Delete asset
    @GetMapping("/delete/{assetID}")
    public String deleteAsset(@PathVariable String assetID, RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(assetID);
        redirectAttributes.addFlashAttribute("successMessage", "Asset successfully deleted!");
        return "redirect:/Asset";
    }

    // Search assets
    @GetMapping("/search")
    public String searchAssets(@RequestParam String name, Model model) {
        model.addAttribute("assets", assetService.searchAssetsByName(name));
        return "Asset/Asset_home";
    }

    // List active assets
    @GetMapping("/active")
    public String listActiveAssets(Model model) {
        model.addAttribute("assets", assetService.getActiveAssets());
        return "Asset/Asset_home";
    }

    // Handle root URL
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/Asset";
    }
}