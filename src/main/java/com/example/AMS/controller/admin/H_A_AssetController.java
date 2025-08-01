package com.example.AMS.controller.admin;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class H_A_AssetController {
    private final H_AssetService assetService;

    public H_A_AssetController(H_AssetService assetService) {
        this.assetService = assetService;
    }
    // Show all assets and provide empty asset for modal form
    @GetMapping("/adminAsset")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String showAssets(Model model) {
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("asset", new Asset());
        return "Asset/admin/AddAsset";
    }

    // Handle asset add from modal form
    @PostMapping("/adminAsset")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String addAsset(@ModelAttribute("asset") Asset asset, Model model) {
        assetService.saveAsset(asset);
        model.addAttribute("success", true);
        // After adding, reload all assets and show success
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("asset", new Asset());
        return "Asset/admin/AddAsset";
    }

}
