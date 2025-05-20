package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.Service.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VendorController {

    @Autowired
    private VendorService service;

    @GetMapping("/vendors")
    public String viewVendors(Model model) {
        model.addAttribute("vendors", service.getAllVendors());
        return "list";
    }

    @GetMapping("/vendors/register")
    public String showForm(Model model) {
        model.addAttribute("vendor", new Vendor(0L, "", "", 0.0, "","",""));
        return "register";
    }

    @PostMapping("/vendors/save")
    public String save(@ModelAttribute Vendor vendor) {
        service.addVendor(vendor);
        return "redirect:/login";
    }

    @GetMapping("/vendors/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("vendor", service.getVendorById(id));
        return "edit";
    }

    @PostMapping("/vendors/update")
    public String update(@ModelAttribute Vendor vendor) {
        service.updateVendor(vendor.getId(), vendor);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteVendor(id);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/view/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        model.addAttribute("vendor", service.getVendorById(id));
        return "profile";
    }

    @PostMapping("/vendor/login")
    public String vendorLogin(@RequestParam String email, @RequestParam String password,
                              HttpSession session, Model model) {
        Vendor loggedVendor = service.vendorLogin(email, password);
        if (loggedVendor != null) {
            session.setAttribute("loggedVendor", loggedVendor);
            model.addAttribute("vendor", loggedVendor);
            return "profile";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This will render login.html from templates
    }
    @GetMapping("/vendor/profile")
    public String vendorProfile(HttpSession session, Model model) {
        Vendor vendor = (Vendor) session.getAttribute("loggedVendor");
        if (vendor != null) {
            model.addAttribute("vendor", vendor);
            return "profile";
        } else {
            return "redirect:/login";
        }
    }


}