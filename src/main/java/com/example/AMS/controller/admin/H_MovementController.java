package com.example.AMS.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class H_MovementController {

    @GetMapping("/hMovement")
    public String hMovement(Model model) {
        // Reuse the existing director Movement template
        return "Movement/Movement";
    }
}
