package com.WeddingPlanning.Backend.Service;

import com.WeddingPlanning.Backend.Model.Admin;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final String ADMIN_FILE = "admins.txt";

    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                admins.add(Admin.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public void addAdmin(Admin admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            writer.write(admin.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAdmin(Admin updatedAdmin) {
        List<Admin> admins = getAllAdmins();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (Admin admin : admins) {
                if (admin.getUserId().equals(updatedAdmin.getUserId())) {
                    writer.write(updatedAdmin.toString());
                } else {
                    writer.write(admin.toString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAdmin(String userId) {
        List<Admin> admins = getAllAdmins();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE))) {
            for (Admin admin : admins) {
                if (!admin.getUserId().equals(userId)) {
                    writer.write(admin.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
