package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.M_LocationService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class H_AssetController {

    private final H_AssetService assetService;
    private final M_LocationService locationService;

    public H_AssetController(H_AssetService assetService, M_LocationService locationService) {
        this.assetService = assetService;
        this.locationService = locationService;
    }

    // Show list of all assets
    @GetMapping("/")
    public String showAllAssets(Model model) {
        List<Asset> assetsList = assetService.getAllAssets();
        model.addAttribute("assetsList", assetsList);
        return "Asset_home";
    }

    // Show form for creating a new asset
    @GetMapping("/create")
    public String createAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        return "Asset_create";
    }

    // Handle form submission to save new or edited asset
    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            return "Asset_create";
        }

        assetService.saveAsset(asset);
        redirectAttributes.addFlashAttribute("successMessage", "Asset saved successfully!");
        return "redirect:/asset/home";
    }

    // Show form to edit an existing asset
    @GetMapping("/edit/{assetID}")
    public String showEditForm(@PathVariable("assetID") String assetID, Model model) {
        Optional<Asset> optionalAsset = assetService.getAssetById(assetID);
        if (optionalAsset.isEmpty()) {
            return "redirect:/asset/home";
        }
        model.addAttribute("asset", optionalAsset.get());
        model.addAttribute("locations", locationService.getAllLocations());
        return "Asset_edit";
    }

    // View details of an asset
    @GetMapping("/view/{assetID}")
    public String viewAsset(@PathVariable("assetID") String assetID, Model model) {
        Optional<Asset> asset = assetService.getAssetById(assetID);
        if (asset.isPresent()) {
            model.addAttribute("asset", asset.get());
            return "Asset_show";
        } else {
            return "redirect:/asset/home";
        }
    }

    // Update existing asset
    @PostMapping("/update")
    public String updateAsset(@Valid @ModelAttribute("asset") Asset asset,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            return "Asset_edit";
        }

        assetService.updateAsset(asset.getAssetID(), asset);
        redirectAttributes.addFlashAttribute("successMessage", "Asset updated successfully!");
        return "redirect:/asset/home";
    }

    // Delete an asset by its ID
    @GetMapping("/delete/{assetID}")
    public String deleteAsset(@PathVariable("assetID") String assetID,
                              RedirectAttributes redirectAttributes) {
        assetService.deleteAsset(assetID);
        redirectAttributes.addFlashAttribute("successMessage", "Asset deleted successfully!");
        return "redirect:/asset/home";
    }
}
