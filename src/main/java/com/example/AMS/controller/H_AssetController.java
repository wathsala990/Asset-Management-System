package com.example.AMS.controller;

import com.example.AMS.model.Asset;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.H_AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/assets")
@CrossOrigin(origins = "*") // allow frontend requests from any origin
public class H_AssetController {

    @Autowired
    private H_AssetService assetService;

    @PostMapping
    public Asset addAsset(@RequestBody Asset asset) {
        return assetService.createAsset(asset);
    }

    @GetMapping
    public List<Asset> getAllAssets() {
        return assetService.getAllAssets();
    }

    @GetMapping("/{id}")
    public Optional<Asset> getAssetById(@PathVariable String id) {
        return assetService.getAssetById(id);
    }

    @PutMapping("/{id}")
    public Asset updateAsset(@PathVariable String id, @RequestBody Asset asset) {
        return assetService.updateAsset(id, asset);
    }

    @DeleteMapping("/{id}")
    public void deleteAsset(@PathVariable String id) {
        assetService.deleteAsset(id);
    }
}
