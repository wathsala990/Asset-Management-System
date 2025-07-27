package com.example.AMS.controller.director; // Changed package to include 'director'

import com.example.AMS.model.AssetUser;
import com.example.AMS.service.L_AssetUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping // Base mapping for director-related paths
public class L_UserHistoryController { // Renamed class
    private final L_AssetUserService assetUserService;

    public L_UserHistoryController(L_AssetUserService assetUserService) {
        this.assetUserService = assetUserService;
    }

    @GetMapping("/user-history") // Relative to /
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String getUserHistory(Model model,
                                 Authentication authentication) {
        List<com.example.AMS.dto.L_UserHistoryDto> userHistories = assetUserService.getAllUserHistoryDtos();

        // Check if the user has only ROLE_USER (not other higher roles)
        boolean isRegularUser = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_USER"::equals) &&
                authentication.getAuthorities().size() == 1;

        model.addAttribute("userHistories", userHistories);
        model.addAttribute("isRegularUser", isRegularUser);
        return "UserHistory/UserHistory";
    }

    @GetMapping("/user-history/view/{id}") // Relative to /
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String viewHistoryRecord(@PathVariable Long id, Model model) {
        AssetUser history = assetUserService.getUserHistoryById(id);
        model.addAttribute("history", history);
        return "UserHistory/ViewHistory";
    }
}