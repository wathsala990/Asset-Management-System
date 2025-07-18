package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.M_LocationService;
import com.example.AMS.service.VenderService;
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

    @Autowired
    private VenderService venderService;

    // Home page - List all assets
    @GetMapping("")
    public String getAssetsPage(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        return "Asset/Asset_home";
    }

    // Show add asset form
    @GetMapping("/create")
    public String createAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("venders", venderService.getAllVenders());
        model.addAttribute("assetTypes", List.of(
                "Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"
        ));
        return "Asset/Asset_create";
    }

    // Save new asset
    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders()); // ✅ FIX: missing previously
            model.addAttribute("assetTypes", List.of(
                    "Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"
            ));
            return "Asset/Asset_create";
        }

        assetService.saveAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset information saved successfully");
        return "redirect:/Asset";
    }

    // Show edit form
    @GetMapping("/{assetId}/edit")
    public String showEditForm(@PathVariable String assetId, Model model) {
        Asset asset = assetService.getAssetById(assetId);
        model.addAttribute("asset", asset);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("venders", venderService.getAllVenders()); // ✅ Also needed in edit
        model.addAttribute("assetTypes", List.of(
                "Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"
        ));
        return "Asset/Asset_edit";
    }

    // Handle edit (update) submission
    @PostMapping("/{assetId}/update")
    public String updateAsset(@PathVariable String assetId,
                              @Valid @ModelAttribute("asset") Asset asset,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders()); // ✅ Again, needed
            model.addAttribute("assetTypes", List.of(
                    "Laptop", "Desktop", "Monitor", "Printer", "Server", "Network Equipment"
            ));
            return "Asset/Asset_edit";
        }

        asset.setAssetId(assetId);
        assetService.saveAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset updated successfully");
        return "redirect:/Asset";
    }

    // Delete asset
    @GetMapping("/{assetId}/delete")
    public String deleteAsset(@PathVariable String assetId, RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(assetId);
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

    // Show single asset
    @GetMapping("/show")
    public String showAsset(@RequestParam String assetId, Model model) {
        Asset asset = assetService.getAssetById(assetId);
        model.addAttribute("asset", asset);
        return "Asset/Asset_show";
    }

    // Handle root URL
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/Asset";
    }
}
