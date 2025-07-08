package com.example.AMS.controller;

import com.example.AMS.model.YearlyVerification;
import com.example.AMS.service.H_AssetService;
import com.example.AMS.service.H_UserService;
import com.example.AMS.service.H_YearlyVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verifications")
public class YearlyVerificationController {
    @Autowired
    private H_YearlyVerificationService verificationService;

    @Autowired
    private H_AssetService assetService;

    @Autowired
    private H_UserService userService;

    @GetMapping
    public String listVerifications(Model model) {
        model.addAttribute("verifications", verificationService.getAllVerifications());
        model.addAttribute("verifiedCount", verificationService.getVerifiedAssetsCount());
        return "Verifications/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("verification", new YearlyVerification());
//        model.addAttribute("assets", assetService.getActiveAssets());
        model.addAttribute("users", userService.getAllUsers());
        return "Verifications/add";
    }

    @PostMapping("/save")
    public String saveVerification(@ModelAttribute YearlyVerification verification) {
        verificationService.saveVerification(verification);
        return "redirect:/Verifications";
    }

//    @GetMapping("/edit/{verificationID}")
//    public String showEditForm(@PathVariable String verificationID, Model model) {
//        H_YearlyVerification verification = verificationService.getVerificationById(verificationID);
//        model.addAttribute("verification", verification);
//        model.addAttribute("assets", assetService.getActiveAssets());
//        model.addAttribute("users", userService.getAllUsers());
//        return "verifications/edit";
//    }

    @GetMapping("/delete/{verificationID}")
    public String deleteVerification(@PathVariable String verificationID) {
        verificationService.deleteVerification(verificationID);
        return "redirect:/verifications";
    }

    @GetMapping("/asset/{assetID}")
    public String viewAssetVerifications(@PathVariable String assetID, Model model) {
        model.addAttribute("verifications", verificationService.getVerificationsByAsset(assetID));
        model.addAttribute("asset", assetService.getAssetById(assetID));
        return "verifications/asset-verifications";
    }

    @GetMapping("/user/{userID}")
    public String viewUserVerifications(@PathVariable String userID, Model model) {
        model.addAttribute("verifications", verificationService.getVerificationsByUser(userID));
        model.addAttribute("user", userService.getUserById(userID));
        return "verifications/user-verifications";
    }
}