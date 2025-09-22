package com.example.AMS.controller.admin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

import com.example.AMS.model.Asset;
import com.example.AMS.model.User;
import com.example.AMS.repository.UserRepository;
import com.example.AMS.service.H_AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private com.example.AMS.service.M_InvoiceService invoiceService; // Service to fetch invoice numbers

    public H_A_AssetController(H_AssetService assetService) {
        this.assetService = assetService;
    }

    // Soft delete asset by id
    @PostMapping("/adminAsset/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String softDeleteAsset(@RequestParam("id") String assetId, Model model) {
        Asset asset = assetService.getAssetById(assetId);
        if (asset != null) {
            asset.setDeleted(true);
            assetService.saveAsset(asset);
        }
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("success", true);
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        return "Asset/admin/AddAsset";
    }

    // Restore soft deleted asset
    @PostMapping("/adminAsset/restore")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String restoreAsset(@RequestParam("id") String assetId, Model model) {
        assetService.restoreAsset(assetId);
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        return "Asset/admin/AddAsset";
    }

    // Permanently delete asset
    @PostMapping("/adminAsset/permanent-delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String permanentDeleteAsset(@RequestParam("id") String assetId, Model model) {
        assetService.deleteAssetPermanently(assetId);
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        return "Asset/admin/AddAsset";
    }
    // Show all assets and provide empty asset for modal form, also add user info for header
    @GetMapping("/adminAsset")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String showAssets(Model model, Authentication authentication) {
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        // Add user info for header
        if (authentication != null) {
            String username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                model.addAttribute("username", user.getUsername());
                model.addAttribute("email", user.getEmail());
                String role = user.getRoles().stream().findFirst().map(r -> r.getName().replace("ROLE_", "")).orElse("");
                model.addAttribute("role", role);
            } else {
                model.addAttribute("username", username);
                model.addAttribute("email", "");
                model.addAttribute("role", "");
            }
        } else {
            model.addAttribute("username", "");
            model.addAttribute("email", "");
            model.addAttribute("role", "");
        }
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
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        return "Asset/admin/AddAsset";
    }

    // Handle asset edit from modal form
    @PostMapping("/adminAsset/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String editAsset(@PathVariable("id") String assetId, @ModelAttribute Asset updatedAsset, Model model) {
        Asset existingAsset = assetService.getAssetById(assetId);
        if (existingAsset != null) {
            // Update fields
            existingAsset.setName(updatedAsset.getName());
            existingAsset.setType(updatedAsset.getType());
            existingAsset.setSerialNumber(updatedAsset.getSerialNumber());
            existingAsset.setModel(updatedAsset.getModel());
            existingAsset.setSpecification(updatedAsset.getSpecification());
            existingAsset.setInvoiceNumber(updatedAsset.getInvoiceNumber());
            existingAsset.setActivityStatus(updatedAsset.isActivityStatus());
            existingAsset.setPurchaseDate(updatedAsset.getPurchaseDate());
            existingAsset.setWarrantyId(updatedAsset.getWarrantyId());
            existingAsset.setWarrantyPeriod(updatedAsset.getWarrantyPeriod());
            existingAsset.setWarrantyDate(updatedAsset.getWarrantyDate());
            
            assetService.saveAsset(existingAsset);
        }
        model.addAttribute("success", true);
        model.addAttribute("assets", assetService.getAllAssets());
        model.addAttribute("deletedAssets", assetService.getDeletedAssets());
        model.addAttribute("asset", new Asset());
        model.addAttribute("invoiceNumbers", invoiceService.getAllInvoiceNumbers());
        return "Asset/admin/AddAsset";
    }

    // AJAX endpoint for asset edit
    @PostMapping("/adminAsset/edit-ajax/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    @ResponseBody
    public ResponseEntity<String> editAssetAjax(@PathVariable("id") String assetId, @ModelAttribute Asset updatedAsset) {
        System.out.println("=== Asset Edit Request ===");
        System.out.println("Asset ID: " + assetId);
        System.out.println("Updated Asset Name: " + updatedAsset.getName());
        System.out.println("Updated Asset Type: " + updatedAsset.getType());
        System.out.println("Updated Asset Serial: " + updatedAsset.getSerialNumber());
        System.out.println("Updated Asset Model: " + updatedAsset.getModel());
        System.out.println("Updated Asset Invoice: " + updatedAsset.getInvoiceNumber());
        System.out.println("Updated Asset Activity Status: " + updatedAsset.isActivityStatus());
        
        try {
            Asset existingAsset = assetService.getAssetById(assetId);
            if (existingAsset == null) {
                System.out.println("Asset not found with ID: " + assetId);
                return ResponseEntity.badRequest().body("Asset not found");
            }
            
            System.out.println("Found existing asset: " + existingAsset.getName());
            
            // Update fields
            existingAsset.setName(updatedAsset.getName());
            existingAsset.setType(updatedAsset.getType());
            existingAsset.setSerialNumber(updatedAsset.getSerialNumber());
            existingAsset.setModel(updatedAsset.getModel());
            existingAsset.setSpecification(updatedAsset.getSpecification());
            existingAsset.setInvoiceNumber(updatedAsset.getInvoiceNumber());
            existingAsset.setActivityStatus(updatedAsset.isActivityStatus());
            existingAsset.setPurchaseDate(updatedAsset.getPurchaseDate());
            existingAsset.setWarrantyId(updatedAsset.getWarrantyId());
            existingAsset.setWarrantyPeriod(updatedAsset.getWarrantyPeriod());
            existingAsset.setWarrantyDate(updatedAsset.getWarrantyDate());
            
            Asset savedAsset = assetService.saveAsset(existingAsset);
            System.out.println("Asset saved successfully: " + savedAsset.getAssetId());
            
            return ResponseEntity.ok("Asset updated successfully");
            
        } catch (Exception e) {
            System.out.println("Error updating asset: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Failed to update asset: " + e.getMessage());
        }
    }

    @Autowired
    private UserRepository userRepository;

}
