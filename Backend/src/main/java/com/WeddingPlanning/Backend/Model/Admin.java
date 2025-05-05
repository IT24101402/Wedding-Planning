package com.WeddingPlanning.Backend.Model;

public class Admin extends User {
    private String password;

    public Admin() {}

    public Admin(String userId, String userName, String password) {
        super(userId, userName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return userId + "," + userName + "," + password;
    }

    public static Admin fromString(String line) {
        String[] parts = line.split(",");
        return new Admin(parts[0], parts[1], parts[2]);
    }

}
