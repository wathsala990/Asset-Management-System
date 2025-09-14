package com.example.AMS.controller.admin;

import com.example.AMS.model.Invoice;
import com.example.AMS.model.User;
import com.example.AMS.model.Vender;
import com.example.AMS.service.M_InvoiceService;
import com.example.AMS.repository.VenderRepository;
import com.example.AMS.repository.UserRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/adminInvoice")
public class M_A_InvoiceController {
    
    private final UserRepository userRepository;
    private final VenderRepository venderRepository;
    private final M_InvoiceService invoiceService;

    public M_A_InvoiceController(M_InvoiceService invoiceService, VenderRepository venderRepository, UserRepository userRepository) {
        this.invoiceService = invoiceService;
        this.venderRepository = venderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/view/{invoiceNumber}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String viewInvoice(@PathVariable("invoiceNumber") String invoiceNumber, Model model) {
        Invoice invoice = invoiceService.getInvoiceById(invoiceNumber);
        model.addAttribute("invoice", invoice);
        return "Invoice/admin/ViewInvoice";
    }
    // Vendor name auto-suggest endpoint
    @GetMapping("/vendors/suggest")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public @ResponseBody List<String> suggestVendors(@RequestParam("query") String query) {
        List<Vender> vendors = venderRepository.findByVenderNameContainingIgnoreCase(query);
        List<String> names = new ArrayList<>();
        for (Vender v : vendors) {
            names.add(v.getVenderName());
        }
        return names;
    }

    // Vendor details autofill endpoint
    @GetMapping("/vendors/details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public @ResponseBody Optional<Vender> getVendorDetails(@RequestParam("venderName") String venderName) {
        return venderRepository.findByVenderName(venderName);
    }

    @GetMapping("")
    public String showInvoices(@RequestParam(value = "invoiceNumberFilter", required = false) String invoiceNumberFilter, Model model, Authentication authentication) {
        List<Invoice> invoices;
        if (invoiceNumberFilter != null && !invoiceNumberFilter.isEmpty()) {
            invoices = invoiceService.findByInvoiceNumberContaining(invoiceNumberFilter);
        } else {
            invoices = invoiceService.getAllInvoices();
        }
        
        // Populate vendor details for each invoice
        for (Invoice invoice : invoices) {
            if (invoice.getVender() != null) {
                if (invoice.getVenderName() == null || invoice.getVenderName().isEmpty()) {
                    invoice.setVenderName(invoice.getVender().getVenderName());
                }
                if (invoice.getAddress() == null || invoice.getAddress().isEmpty()) {
                    invoice.setAddress(invoice.getVender().getAddress());
                }
                if (invoice.getContactNo() == 0) {
                    invoice.setContactNo(invoice.getVender().getContactNo());
                }
            }
        }
        
        model.addAttribute("invoices", invoices);
        model.addAttribute("invoiceNumberFilter", invoiceNumberFilter);
        model.addAttribute("invoice", new Invoice());

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

        return "Invoice/admin/Invoice";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String addInvoice(
        @ModelAttribute("invoice") Invoice invoice,
        Model model) {
        invoiceService.saveInvoice(
            invoice,
            invoice.getVenderName(),
            invoice.getAddress(),
            invoice.getContactNo()
        );
        model.addAttribute("success", true);
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        model.addAttribute("invoice", new Invoice());
        return "Invoice/admin/Invoice";
    }

    @PostMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_DIRECTOR')")
    public String editInvoice(
        @RequestParam("invoiceNumber") String invoiceNumber,
        @RequestParam("orderId") String orderId,
        @RequestParam("invoiceDate") String invoiceDate,
        @RequestParam("invoiceCost") String invoiceCost,
        @RequestParam("venderName") String venderName,
        @RequestParam("address") String address,
        @RequestParam("contactNo") String contactNo,
        @RequestParam(value = "remark", required = false) String remark,
        Model model) {
        
        try {
            Invoice existingInvoice = invoiceService.getInvoiceById(invoiceNumber);
            if (existingInvoice != null) {
                existingInvoice.setOrderId(orderId);
                existingInvoice.setInvoiceDate(java.time.LocalDate.parse(invoiceDate));
                existingInvoice.setInvoiceCost(invoiceCost); // Keep as String
                existingInvoice.setVenderName(venderName);
                existingInvoice.setAddress(address);
                existingInvoice.setRemark(remark);
                
                // Convert String to int for contactNo, handle parsing errors
                int contactNumber;
                try {
                    contactNumber = Integer.parseInt(contactNo.replaceAll("[^0-9]", "")); // Remove non-numeric characters
                } catch (NumberFormatException e) {
                    contactNumber = 0; // Default value if parsing fails
                }
                existingInvoice.setContactNo(contactNumber);
                
                invoiceService.saveInvoice(
                    existingInvoice,
                    venderName,
                    address,
                    contactNumber // Pass int instead of String
                );
                model.addAttribute("editSuccess", true);
            }
        } catch (Exception e) {
            model.addAttribute("editError", true);
        }
        
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        model.addAttribute("invoice", new Invoice());
        return "Invoice/admin/Invoice";
    }
}
