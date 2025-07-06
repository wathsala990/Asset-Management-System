package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
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
public class H_AssetController {

    private final H_AssetService assetService;

    @Autowired
    public H_AssetController(H_AssetService assetService) {
        this.assetService = assetService;
    }

    // Home page displaying all asset records
    @GetMapping("/")
    public String home(@RequestParam(value = "assetID", required = false) String assetID,
                       Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "templates/asset_home"; // e.g., templates/asset_home.html
    }

    // Show form for creating a new asset
    @GetMapping("/create")
    public String createAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        return "templates/asset_create";
    }

    // Handle form submission for creating or editing an asset
    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "asset_create";
        }
        assetService.createAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset saved successfully!");
        return "redirect:/";
    }

    // Display a specific asset record
    @GetMapping("/asset/{assetID}")
    public String showAsset(@PathVariable String assetID, Model model) {
        Optional<Asset> optionalAsset = assetService.getAssetById(assetID);
        optionalAsset.ifPresent(asset -> model.addAttribute("asset", asset));
        return "asset_viewdetails";
    }

    // Show form to edit an existing asset record
    @GetMapping("/asset/{assetID}/edit")
    public String editAsset(@PathVariable String assetID, Model model) {
        Optional<Asset> asset = assetService.getAssetById(assetID);
        model.addAttribute("asset", asset.orElse(null));
        return "asset_create";
    }

    // Handle deletion of an asset record
    @GetMapping("/asset/{assetID}/delete")
    public String deleteAsset(@PathVariable String assetID,
                              RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(assetID);
        redirectAttributes.addFlashAttribute("message", "Asset deleted successfully!");
        return "redirect:/";
    }

    // View Page Mapping (Optional View-Only Page)
    @GetMapping("/view")
    public String viewAssets(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "asset_view"; // e.g., templates/asset_view.html
    }
}
