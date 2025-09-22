package com.example.AMS.controller.admin;

import com.example.AMS.model.User;
import com.example.AMS.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.AMS.model.Maintain;
import com.example.AMS.repository.S_MaintainRepository;
import com.example.AMS.repository.AssetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/adminMaintain")
public class S_A_MaintainController {
    private final UserRepository userRepository;

    public S_A_MaintainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private S_MaintainRepository maintainRepository;

    @Autowired
    private AssetRepository assetRepository;

    // List all maintenance records (not deleted)
    @GetMapping
    public String listMaintains(Model model, Authentication authentication) {
        List<Maintain> maintains = maintainRepository.findAll().stream()
            .filter(m -> !m.isDeleted())
            .toList();
        model.addAttribute("maintains", maintains);
        model.addAttribute("maintain", new Maintain()); // For modal or form
        model.addAttribute("assets", assetRepository.findAll());

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

        return "Maintain/admin/list";
    }

    // Recycle bin: show deleted records
    @GetMapping("/recycle-bin")
    public String showRecycleBin(Model model) {
        List<Maintain> deletedMaintains = maintainRepository.findAll().stream()
            .filter(Maintain::isDeleted)
            .toList();
        model.addAttribute("deletedMaintains", deletedMaintains);
        return "Maintain/admin/recycle-bin";
    }
    // Show form to add new maintenance (redirect to main page with modal)
    @GetMapping("/add")
    public String showAddForm(Model model) {
        return "redirect:/admin/adminMaintain";
    }

    // Save a maintenance record
    @PostMapping("/save")
    public String saveMaintain(@RequestParam("maintainId") String maintainId,
                              @RequestParam("assetId") String assetId,
                              @RequestParam("maintainsDate") String maintainsDate,
                              @RequestParam("nextMaintainDate") String nextMaintainDate,
                              @RequestParam("description") String description,
                              @RequestParam("cost") float cost) {
        
        // Create or update maintenance record
        Maintain maintain = maintainRepository.findById(maintainId).orElse(new Maintain());
        maintain.setMaintainId(maintainId);
        maintain.setMaintainsDate(LocalDate.parse(maintainsDate));
        maintain.setNextMaintainDate(LocalDate.parse(nextMaintainDate));
        maintain.setDescription(description);
        maintain.setCost(cost);
        
        // Set the asset based on the assetId
        if (assetId != null && !assetId.isEmpty()) {
            assetRepository.findById(assetId).ifPresent(maintain::setAsset);
        }
        
        maintainRepository.save(maintain);
        return "redirect:/admin/adminMaintain";
    }

    // Edit a maintenance record (redirect to main page with modal)
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") String maintainId, Model model) {
        return "redirect:/admin/adminMaintain";
    }

    // Soft delete a maintenance record
    @GetMapping("/delete")
    public String deleteMaintain(@RequestParam("id") String maintainId) {
        Optional<Maintain> maintainOpt = maintainRepository.findById(maintainId);
        if (maintainOpt.isPresent()) {
            Maintain maintain = maintainOpt.get();
            maintain.setDeleted(true);
            maintainRepository.save(maintain);
        }
        return "redirect:/admin/adminMaintain";
    }
    // Restore a deleted maintenance record
    @GetMapping("/restore")
    public String restoreMaintain(@RequestParam("id") String maintainId) {
        Optional<Maintain> maintainOpt = maintainRepository.findById(maintainId);
        if (maintainOpt.isPresent()) {
            Maintain maintain = maintainOpt.get();
            maintain.setDeleted(false);
            maintainRepository.save(maintain);
        }
        return "redirect:/admin/adminMaintain/recycle-bin";
    }
    
    // Permanently delete a maintenance record
    @GetMapping("/permanent-delete")
    public String permanentDeleteMaintain(@RequestParam("id") String maintainId) {
        maintainRepository.deleteById(maintainId);
        return "redirect:/admin/adminMaintain/recycle-bin";
    }
}


