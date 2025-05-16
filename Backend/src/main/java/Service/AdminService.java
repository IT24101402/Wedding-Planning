package com.WeddingPlanning.Backend.Service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final String adminUsername = "admin";
    private final String adminPassword = "admin123";

    public boolean authenticate(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }
}
