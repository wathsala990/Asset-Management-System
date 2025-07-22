package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Asset")
public class H_AssetController {

    private final H_AssetService assetService;

    @Autowired
    public H_AssetController(H_AssetService assetService) {
        this.assetService = assetService;
    }

    // Get all assets
    @GetMapping
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }

    // Get asset by ID
    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable String assetId) {
        Optional<Asset> asset = assetService.getAssetById(assetId);
        return asset.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new asset
    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestBody Asset asset) {
        if (assetService.assetExists(asset.getAssetId())) {
            return ResponseEntity.badRequest().build();
        }

        Asset createdAsset = assetService.createAsset(asset);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{assetId}")
                .buildAndExpand(createdAsset.getAssetId())
                .toUri();

        return ResponseEntity.created(location).body(createdAsset);
    }

    // Update existing asset
    @PutMapping("/{assetId}")
    public ResponseEntity<Asset> updateAsset(
            @PathVariable String assetId,
            @RequestBody Asset assetDetails) {
        try {
            Asset updatedAsset = assetService.updateAsset(assetId, assetDetails);
            return ResponseEntity.ok(updatedAsset);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete asset
    @DeleteMapping("/{assetId}")
    public ResponseEntity<Void> deleteAsset(@PathVariable String assetId) {
        if (!assetService.assetExists(assetId)) {
            return ResponseEntity.notFound().build();
        }
        assetService.deleteAsset(assetId);
        return ResponseEntity.noContent().build();
    }

    // Search assets by ID or name
    @GetMapping("/search")
    public ResponseEntity<List<Asset>> searchAssets(@RequestParam String term) {
        List<Asset> assets = assetService.searchAssets(term);
        return ResponseEntity.ok(assets);
    }

    // Get assets by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Asset>> getAssetsByStatus(@PathVariable String status) {
        List<Asset> assets = assetService.getAssetsByStatus(status);
        return ResponseEntity.ok(assets);
    }

    // Get assets by vendor ID
    @GetMapping("/vendor/{venderId}")
    public ResponseEntity<List<Asset>> getAssetsByVendorId(@PathVariable String venderId) {
        List<Asset> assets = assetService.getAssetsByVenderId(venderId);
        return ResponseEntity.ok(assets);
    }
}