package com.example.AMS.controller.admin; // Changed package to include 'admin'

import com.example.AMS.model.Asset;
import com.example.AMS.model.AssetUser;
import com.example.AMS.model.User;
import com.example.AMS.service.L_AssetUserService;
import com.example.AMS.repository.UserRepository;
import com.example.AMS.dto.L_UserHistoryDto;
import com.example.AMS.dto.AddUserHistoryDto;
import com.example.AMS.dto.UserSuggestDto;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        List<L_UserHistoryDto> userHistories = assetUserService.getAllUserHistoryDtos();
        
        // Debug logging
        System.out.println("Retrieved " + userHistories.size() + " user history records");
        if (!userHistories.isEmpty()) {
            L_UserHistoryDto first = userHistories.get(0);
            System.out.println("First record - User: " + first.getUserName() + 
                             ", Asset: " + first.getAssetName() + 
                             ", AssetId: " + first.getAssetId() + 
                             ", Serial: " + first.getAssetSerialNumber());
        }

        // Check if the user has only ROLE_USER (not other higher roles)
        boolean isRegularUser = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals) &&
                authentication.getAuthorities().size() == 1;

        // Add user info for header
        String username = "";
        String email = "";
        String role = "";
        
        if (authentication != null) {
            username = authentication.getName();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                email = user.getEmail();
                role = user.getRoles().stream().findFirst().map(r -> r.getName().replace("ROLE_", "")).orElse("");
            }
        }
        
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);

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
    public @ResponseBody List<Asset> suggestAssets(@RequestParam("query") String query) {
        return assetUserService.suggestAssets(query);
    }

    // User auto-suggest endpoint
    @GetMapping("/users/suggest")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public @ResponseBody List<UserSuggestDto> suggestUsers(@RequestParam("query") String query) {
        return assetUserService.suggestUsers(query);
    }

    // Add new user history endpoint
    @PostMapping("/assetUser/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public @ResponseBody String addAssetUser(@RequestBody AddUserHistoryDto dto) {
        boolean success = assetUserService.addAssetUserHistory(dto);
        return success ? "OK" : "ERROR";
    }
}