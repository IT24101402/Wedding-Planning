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
        System.out.println("DEBUG: Entered /vendors");
        model.addAttribute("vendors", service.getAllVendors());
        return "list";
    }

    @GetMapping("/vendors/register")
    public String showForm(Model model) {
        System.out.println("DEBUG: Entered /vendors/register");
        model.addAttribute("vendor", new Vendor(0L, "", "", 0.0, "", "", ""));
        return "vendorregister";
    }

    @PostMapping("/vendors/save")
    public String save(@ModelAttribute Vendor vendor) {
        System.out.println("DEBUG: Saving vendor: " + vendor.getEmail());
        service.addVendor(vendor);
        return "redirect:/vendor-login";
    }

    @GetMapping("/vendors/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        System.out.println("DEBUG: Editing vendor with ID: " + id);
        model.addAttribute("vendor", service.getVendorById(id));
        return "edit";
    }

    @PostMapping("/vendors/update")
    public String update(@ModelAttribute Vendor vendor) {
        System.out.println("DEBUG: Updating vendor ID: " + vendor.getId());
        service.updateVendor(vendor.getId(), vendor);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/delete/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("DEBUG: Deleting vendor with ID: " + id);
        service.deleteVendor(id);
        return "redirect:/vendors";
    }

    @GetMapping("/vendors/view/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        System.out.println("DEBUG: Viewing profile for vendor ID: " + id);
        model.addAttribute("vendor", service.getVendorById(id));
        return "vendorprofile";
    }

    @PostMapping("/vendor/login")
    public String vendorLogin(@RequestParam String email, @RequestParam String password,
                              HttpSession session, Model model) {
        System.out.println("DEBUG: Attempting login for email: " + email);
        Vendor loggedVendor = service.vendorLogin(email, password);
        if (loggedVendor != null) {
            System.out.println("DEBUG: Login successful for vendor ID: " + loggedVendor.getId());
            session.setAttribute("loggedVendor", loggedVendor);
            model.addAttribute("vendor", loggedVendor);
            return "vendorprofile";
        } else {
            System.out.println("DEBUG: Login failed for email: " + email);
            model.addAttribute("error", "Invalid credentials");
            return "redirect:/vendor-login";
        }
    }

    @GetMapping("/vendor-login")
    public String showLoginPage() {
        System.out.println("DEBUG: Entered /vendor-login");
        return "vendorlogin"; // Renders vendorlogin.html
    }

    @GetMapping("/vendor/profile")
    public String vendorProfile(HttpSession session, Model model) {
        Vendor vendor = (Vendor) session.getAttribute("loggedVendor");
        if (vendor != null) {
            System.out.println("DEBUG: Showing profile for logged vendor ID: " + vendor.getId());
            model.addAttribute("vendor", vendor);
            return "profile";
        } else {
            System.out.println("DEBUG: No vendor in session — redirecting to login");
            return "redirect:/vendor-login";
        }
    }
}
