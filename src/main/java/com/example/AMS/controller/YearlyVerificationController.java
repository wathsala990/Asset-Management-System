package com.example.AMS.controller;

import com.example.AMS.service.L_YearlyVerificationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Year;
import java.util.Collection;

@Controller
public class YearlyVerificationController {

    private final L_YearlyVerificationService yearlyVerificationService;

    public YearlyVerificationController(L_YearlyVerificationService yearlyVerificationService) {
        this.yearlyVerificationService = yearlyVerificationService;
    }

    @GetMapping("/YearlyVerification")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR', 'ROLE_USER')")
    public String showYearlyVerification(@RequestParam(value = "year", required = false) Integer year, 
                                       Model model, 
                                       Authentication authentication) {
        int y = (year != null) ? year : Year.now().getValue();
        model.addAttribute("year", y);
        model.addAttribute("items", yearlyVerificationService.getStatusForYear(y));
        
        // Determine which template to use based on user role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            if ("ROLE_ADMIN".equals(role)) {
                return "Yearly Verification/admin/YearlyVerification";
            } else if ("ROLE_DIRECTOR".equals(role)) {
                return "Yearly Verification/director/YearlyVerification";
            }
        }
        
        // Default to generic template for users
        return "Yearly Verification/YearlyVerification";
    }
}