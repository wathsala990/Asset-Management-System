package com.example.AMS.controller.admin;

import com.example.AMS.dto.UserDto;
import com.example.AMS.service.AdminUserService;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        List<UserDto> users = adminUserService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users"; // This will be the Thymeleaf template name
    }

    @PostMapping("/updateUserRole")
    public String updateUserRole(@RequestParam("userId") Long userId,
                                 @RequestParam("roles") List<String> roles,
                                 RedirectAttributes redirectAttributes) {
        adminUserService.updateUserRoles(userId, roles);
        redirectAttributes.addFlashAttribute("success", "User roles updated successfully.");
        return "redirect:/admin/users";
    }
}