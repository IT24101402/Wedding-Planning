package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.Vendor;
import com.WeddingPlanning.Backend.Service.VendorService;
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
        model.addAttribute("vendor", new Vendor(0L, "", "", 0.0, ""));
        return "register";
    }

    @PostMapping("/vendors/save")
    public String save(@ModelAttribute Vendor vendor) {
        service.addVendor(vendor);
        return "redirect:/vendors";
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
}