package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.model.Location;
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
import java.util.Optional;

@Controller
@RequestMapping("/asset")  // ✅ Use a clear base path
public class H_AssetController {

    private final H_AssetService assetService;
    private final M_LocationService locationService;

    @Autowired
    public H_AssetController(H_AssetService assetService, M_LocationService locationService) {
        this.assetService = assetService;
        this.locationService = locationService;
    }

    // ✅ List all assets (main page)
    @GetMapping("/")
    public String home(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "asset_home"; // File: templates/asset_home.html
    }

    // ✅ Show form to create a new asset
    @GetMapping("/create")
    public String createAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        return "asset_create";
    }

    // ✅ Save asset
    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            return "asset_create";
        }

        assetService.createAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset saved successfully!");
        return "redirect:/asset/";
    }

    // ✅ View asset details
    @GetMapping("/{assetID}")
    public String viewAsset(@PathVariable String assetID, Model model) {
        Optional<Asset> optionalAsset = assetService.getAssetById(assetID);
        if (optionalAsset.isPresent()) {
            model.addAttribute("asset", optionalAsset.get());
            return "asset_viewdetails";
        }
        return "redirect:/asset/";
    }

    // ✅ Edit asset form
    @GetMapping("/{assetID}/edit")
    public String editAsset(@PathVariable String assetID, Model model) {
        Optional<Asset> asset = assetService.getAssetById(assetID);
        if (asset.isPresent()) {
            model.addAttribute("asset", asset.get());
            model.addAttribute("locations", locationService.getAllLocations());
            return "asset_create";
        }
        return "redirect:/asset/";
    }

    // ✅ Delete asset
    @GetMapping("/{assetID}/delete")
    public String deleteAsset(@PathVariable String assetID, RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(assetID);
        redirectAttributes.addFlashAttribute("message", "Asset deleted successfully!");
        return "redirect:/asset/";
    }

    // ✅ Separate view page (optional)
    @GetMapping("/view")
    public String viewAssetList(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "asset_viewdetails";
    }
}
