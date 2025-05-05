package com.WeddingPlanning.Backend.Controller;

import com.WeddingPlanning.Backend.Model.Admin;
import com.WeddingPlanning.Backend.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    @PostMapping("/create")
    public String createAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return "Admin created successfully.";
    }

    @PutMapping("/update")
    public String updateAdmin(@RequestBody Admin admin) {
        adminService.updateAdmin(admin);
        return "Admin updated successfully.";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteAdmin(@PathVariable String userId) {
        adminService.deleteAdmin(userId);
        return "Admin deleted successfully.";
    }
}
