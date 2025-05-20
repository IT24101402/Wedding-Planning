package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin-login")
    public String showLoginPage() {
        return "admin-login";
    }

    @PostMapping("/admin-login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (adminService.authenticate(username, password)) {
            session.setAttribute("admin", true);
            return "redirect:/admin";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "admin-login";
        }
    }

    @GetMapping("/admin-logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin-login";
    }
}
