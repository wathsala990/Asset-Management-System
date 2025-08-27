package com.example.AMS.controller.admin; // Changed package to include 'admin'

import com.example.AMS.model.Asset;
import com.example.AMS.model.AssetUser;
import com.example.AMS.model.User;
import com.example.AMS.service.L_AssetUserService;
import com.example.AMS.repository.UserRepository;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; // Import RequestMapping
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin") // Base mapping for admin-related paths
public class L_A_UserHistoryController { // Renamed class
    private final L_AssetUserService assetUserService;
    private final UserRepository userRepository;

    public L_A_UserHistoryController(L_AssetUserService assetUserService, UserRepository userRepository) {
        this.assetUserService = assetUserService;
        this.userRepository = userRepository;
    }

    @GetMapping("/adminUserHistory") // Relative to /admin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String getUserHistory(Model model,
                                 Authentication authentication) {
        List<com.example.AMS.dto.L_UserHistoryDto> userHistories = assetUserService.getAllUserHistoryDtos();

        // Check if the user has only ROLE_USER (not other higher roles)
        boolean isRegularUser = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals) &&
                authentication.getAuthorities().size() == 1;

        // Add user info for header (like H_A_AssetController)
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

        model.addAttribute("userHistories", userHistories);
        model.addAttribute("isRegularUser", isRegularUser);
        return "UserHistory/admin/UserHistory";
    }

    @GetMapping("/adminUserHistory/view/{id}") // Relative to /admin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String viewHistoryRecord(@PathVariable Long id, Model model) {
        AssetUser history = assetUserService.getUserHistoryById(id);
        model.addAttribute("history", history);
        return "UserHistory/admin/ViewHistory";
    }

    // Asset auto-suggest endpoint
    @GetMapping("/assets/suggest")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public @ResponseBody List<com.example.AMS.model.Asset> suggestAssets(@RequestParam("query") String query) {
        return assetUserService.suggestAssets(query);
    }

    // User auto-suggest endpoint
    @GetMapping("/users/suggest")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public @ResponseBody List<com.example.AMS.dto.UserSuggestDto> suggestUsers(@RequestParam("query") String query) {
        return assetUserService.suggestUsers(query);
    }

    // Add new user history endpoint
    @PostMapping("/assetUser/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public @ResponseBody String addAssetUser(@RequestBody com.example.AMS.dto.AddUserHistoryDto dto) {
        boolean success = assetUserService.addAssetUserHistory(dto);
        return success ? "OK" : "ERROR";
    }
}