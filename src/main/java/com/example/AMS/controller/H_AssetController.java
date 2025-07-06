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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/asset")
public class H_AssetController {

    private final H_AssetService assetService;
    private final M_LocationService locationService;

    @Autowired
    public H_AssetController(H_AssetService assetService, M_LocationService locationService) {
        this.assetService = assetService;
        this.locationService = locationService;
    }

    // ✅ Main Asset List Page
    @GetMapping("/")
    public String home(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "asset_home"; // Template: src/main/resources/templates/asset_home.html
    }

    // ✅ Show form to create a new asset
    @GetMapping("/create")
    public String createAssetForm(Model model) {
        model.addAttribute("asset", new Asset());
        model.addAttribute("locations", locationService.getAllLocations());
        return "asset_create"; // Template: asset_create.html
    }

    // ✅ Save manually created asset
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

    // ✅ Show Excel Upload Page
    @GetMapping("/upload")
    public String showUploadForm() {
        return "asset_upload"; // Create asset_upload.html
    }

    // ✅ Handle Excel Upload (uncomment when ready)
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please select a valid Excel file.");
            return "redirect:/asset/upload";
        }

        try {
            assetService.importFromExcel(file); // Make sure this method exists
            redirectAttributes.addFlashAttribute("message", "Assets uploaded successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Upload failed: " + e.getMessage());
        }

        return "redirect:/asset/";
    }

    // ✅ View individual asset
    @GetMapping("/{assetID}")
    public String viewAsset(@PathVariable String assetID, Model model) {
        Optional<Asset> optionalAsset = assetService.getAssetById(assetID);
        if (optionalAsset.isPresent()) {
            model.addAttribute("asset", optionalAsset.get());
            return "asset_viewdetails"; // Template: asset_viewdetails.html
        }
        return "redirect:/asset/";
    }

    // ✅ Edit asset
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

    // ✅ View All Assets (Alternate Page)
    @GetMapping("/view")
    public String viewAssetList(Model model) {
        List<Asset> assetList = assetService.getAllAssets();
        model.addAttribute("assetList", assetList);
        return "asset_viewdetails";
    }
}
