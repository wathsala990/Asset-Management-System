package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.H_LocationService;
import com.example.AMS.service.H_RoomService;
import com.example.AMS.service.H_VenderService;
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
    private H_LocationService locationService;

    @Autowired
    private H_VenderService venderService;

    @Autowired
    private H_RoomService roomService;


    // List all assets
    @GetMapping
    public String showAssetList(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "Asset/Asset_home";
    }

    // Show asset creation form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("venders", venderService.getAllVenders());
        model.addAttribute("rooms", roomService.getAllRooms());

        return "Asset/Asset_create";
    }

    // Save new asset
    @PostMapping("/create")
    public String createAsset(@Valid @ModelAttribute("asset") Asset asset,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "Asset/Asset_create";
        }

        try {
            assetService.saveAsset(asset);
            redirectAttributes.addFlashAttribute("success", "Asset created successfully!");
            return "redirect:/Asset";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error creating asset: " + e.getMessage());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders());
            model.addAttribute("rooms", roomService.getAllRooms());
            return "Asset/Asset_create";
        }
    }

    @PostMapping("/save")
    public String saveAsset(@Valid @ModelAttribute("asset") Asset asset,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes,
                            Model model) {

        if (bindingResult.hasErrors()) {
            // Re-populate dropdown lists in case of errors
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders());
            model.addAttribute("rooms",roomService.getAllRooms());
            return "Asset_create";
        }

        assetService.saveAsset(asset);
        redirectAttributes.addFlashAttribute("message", "Asset saved successfully!");
        return "redirect:/Asset_home";
    }

    // Show edit form
    @GetMapping("/{assetId}/edit")
    public String showEditForm(@PathVariable String assetId, Model model) {
        Asset asset = assetService.getAssetById(assetId);
        if (asset == null) {
            return "redirect:/Asset";
        }
        model.addAttribute("asset", asset);
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("venders", venderService.getAllVenders());
        return "Asset/Asset_edit";
    }

    // Update existing asset
    @PostMapping("/{assetId}/edit")
    public String updateAsset(@PathVariable String assetId,
                              @Valid @ModelAttribute("asset") Asset asset,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders());
            return "Asset/Asset_edit";
        }

        try {
            asset.setAssetId(assetId); // retain original ID
            assetService.saveAsset(asset);
            redirectAttributes.addFlashAttribute("success", "Asset updated successfully!");
            return "redirect:/Asset";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error updating asset: " + e.getMessage());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("venders", venderService.getAllVenders());
            return "Asset/Asset_edit";
        }
    }

    // Delete asset
    @GetMapping("/{assetId}/delete")
    public String deleteAsset(@PathVariable String assetId,
                              RedirectAttributes redirectAttributes) {
        try {
            assetService.deleteAsset(assetId);
            redirectAttributes.addFlashAttribute("success", "Asset deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting asset: " + e.getMessage());
        }
        return "redirect:/Asset";
    }

    // Show single asset detail
    @GetMapping("/show/{assetId}")
    public String showAssetDetails(@PathVariable String assetId, Model model) {
        Asset asset = assetService.getAssetById(assetId);
        if (asset == null) {
            return "redirect:/Asset";
        }
        model.addAttribute("asset", asset);
        return "Asset/Asset_show";
    }
}
